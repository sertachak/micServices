package com.sha.rabbitdemo.event;

import com.sha.rabbitdemo.reactor.dto.ProductDto;
import com.sha.rabbitdemo.reactor.entity.Product;
import com.sha.rabbitdemo.reactor.rep.ProductRepository;
import com.sha.rabbitdemo.reactor.service.ProductService;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {

    private ProductService productService;

    public ProductEventHandler(ProductService productService) {
        this.productService = productService;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        ProductDto productDto = new ProductDto();
        BeanUtils.copyProperties(productCreatedEvent, productDto);
        Mono<ProductDto> productDtoMono = Mono.just(productDto);
       productService.saveProduct(productDtoMono).block();
       //https://discuss.axoniq.io/t/asynchronous-event-handlers/4283
    }

    @EventHandler
    public void  on(ProductReservedEvent productReservedEvent){
        productService.getProductById(productReservedEvent.getProductId()).map(product -> {
            product.setQty(product.getQty() - productReservedEvent.getQuantity());
            return Mono.just(product);
        }).flatMap(productService::saveProduct).block();
    }
}

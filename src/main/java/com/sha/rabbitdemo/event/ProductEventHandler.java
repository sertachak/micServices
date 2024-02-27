package com.sha.rabbitdemo.event;

import com.sha.rabbitdemo.reactor.entity.Product;
import com.sha.rabbitdemo.reactor.rep.ProductRepository;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductEventHandler {

    private ProductRepository productRepository;

    public ProductEventHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Product created event received: " + productCreatedEvent.getProductId());
        //Product entity = new ProductEntity();
        //BeanUtils.copyProperties(productCreatedEvent, entity);
        //productRepository.save(entity);
    }
}

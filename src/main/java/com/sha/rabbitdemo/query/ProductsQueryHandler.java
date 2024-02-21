package com.sha.rabbitdemo.query;

import com.sha.rabbitdemo.model.ProductRestModel;
import com.sha.rabbitdemo.reactor.rep.ProductRepository;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductsQueryHandler {

    private ProductRepository productRepository;

    public ProductsQueryHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> findProducts(FindProductsQuery query) {
        return productRepository.findAll().map(product -> ProductRestModel.builder()
                .name(product.getName())
                .price(product.getPrice())
                .qty(product.getQty())
                .build()).collectList().block();
    }
}

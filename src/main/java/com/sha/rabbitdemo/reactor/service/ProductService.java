package com.sha.rabbitdemo.reactor.service;

import com.sha.rabbitdemo.reactor.dto.ProductDto;
import com.sha.rabbitdemo.reactor.rep.ProductRepository;
import com.sha.rabbitdemo.reactor.utils.AppUtils;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductService {

    private ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Flux<ProductDto> getAllProducts(){
        return productRepository.findAll().map(AppUtils::toProductDto);
    }

    public Mono<ProductDto> getProductById(String id){
        return productRepository.findById(id).map(AppUtils::toProductDto);
    }

    public Flux<ProductDto> getProductsInRange(Double min, Double max){
        return productRepository.findByPriceBetween(min, max).map(AppUtils::toProductDto);
    }

    public Mono<ProductDto> saveProduct(Mono<ProductDto> productDtoMono){
        return productDtoMono.map(AppUtils::toProduct)
                .flatMap(productRepository::insert)
                .map(AppUtils::toProductDto);
    }
}

package com.sha.rabbitdemo.controller;

import com.sha.rabbitdemo.reactor.dto.ProductDto;
import com.sha.rabbitdemo.reactor.service.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Controller
@RequestMapping("api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/here")
    public Mono<ProductDto> saveProduct(@RequestBody ProductDto productDto) {
        Mono<ProductDto> productDtoMono = Mono.just(productDto);
        final Mono<ProductDto> productDtoMono1 = productService.saveProduct(productDtoMono);
        return productDtoMono1;
    }
}

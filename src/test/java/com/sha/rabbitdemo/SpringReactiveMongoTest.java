package com.sha.rabbitdemo;


import com.sha.rabbitdemo.controller.ProductController;
import com.sha.rabbitdemo.reactor.dto.ProductDto;
import com.sha.rabbitdemo.reactor.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import reactor.core.publisher.Mono;

import  static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ProductController.class)
class SpringReactiveMongoCrudApplicationTests {
    @Autowired
    private WebTestClient webTestClient;
    @MockBean
    private ProductService service;

    @Test
    void addProductTest(){
        Mono<ProductDto> productDtoMono=Mono.just(new ProductDto("102","mobile",1.0,10000));
        when(service.saveProduct(productDtoMono)).thenReturn(productDtoMono);

        webTestClient.post().uri("/api/product/here")
                .body(Mono.just(productDtoMono),ProductDto.class)
                .
                .exchange()
                .expectStatus().isOk()
                .expectBody(ProductDto.class);
    }

}
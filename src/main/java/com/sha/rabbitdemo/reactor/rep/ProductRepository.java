package com.sha.rabbitdemo.reactor.rep;

import com.sha.rabbitdemo.reactor.entity.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ProductRepository extends ReactiveMongoRepository<Product, String>{
    Flux<Product> findByPriceBetween(Double min, Double max);
}

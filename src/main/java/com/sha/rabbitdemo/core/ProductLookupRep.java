package com.sha.rabbitdemo.core;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductLookupRep extends ReactiveMongoRepository<ProductLookupEntity, String>{
    ProductLookupEntity findByIdOrName(String id, String name);
}

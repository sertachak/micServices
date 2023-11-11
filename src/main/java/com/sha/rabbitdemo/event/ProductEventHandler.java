package com.sha.rabbitdemo.event;

import org.axonframework.eventhandling.EventHandler;
import org.springframework.stereotype.Component;

@Component
public class ProductEventHandler {

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        System.out.println("Product created event received: " + productCreatedEvent.getProductId());
    }
}

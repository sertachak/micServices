package com.sha.rabbitdemo.command;

import com.sha.rabbitdemo.core.ProductLookupEntity;
import com.sha.rabbitdemo.core.ProductLookupRep;
import com.sha.rabbitdemo.event.ProductCreatedEvent;
import com.sha.rabbitdemo.reactor.utils.AppUtils;
import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
@ProcessingGroup("product-group")
public class ProductLookupEventsHandler {

    private final ProductLookupRep productLookupRep;

    public ProductLookupEventsHandler(ProductLookupRep productLookupRep) {
        this.productLookupRep = productLookupRep;
    }

    @EventHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        ProductLookupEntity entity = new ProductLookupEntity();
        BeanUtils.copyProperties(productCreatedEvent, entity);
        productLookupRep.insert(entity).block();
    }
}

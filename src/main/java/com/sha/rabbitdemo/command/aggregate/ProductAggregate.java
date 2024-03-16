package com.sha.rabbitdemo.command.aggregate;

import com.sha.rabbitdemo.command.ProductCreateCommand;
import com.sha.rabbitdemo.event.ProductCreatedEvent;
import com.sha.rabbitdemo.event.ProductReservedEvent;
import com.sha.rabbitdemo.microservices.saga.ReserveProductCommand;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.modelling.command.TargetAggregateIdentifier;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;

@Aggregate
public class ProductAggregate {

    @AggregateIdentifier
    private String productId;
    private String name;
    private BigDecimal price;
    private Integer qty;

    public ProductAggregate() {//us ed by axon framework
    }

    @CommandHandler
    public ProductAggregate(ProductCreateCommand createProductCommand) {
        //You can perform all the validations
        ProductCreatedEvent productCreatedEvent =
                new ProductCreatedEvent();

        BeanUtils.copyProperties(createProductCommand, productCreatedEvent);

        AggregateLifecycle.apply(productCreatedEvent);// product created event is dispatched after this line
    }

    @CommandHandler
    public void handle(ReserveProductCommand reserveProductCommand) {
        //You can perform all the validations
        if(qty < reserveProductCommand.getQuantity()){ //according to the targetIdentifierAggregate we can take a look at the current state of the aggregate
            throw new IllegalArgumentException("Insufficient product quantity.");
        }

        ProductReservedEvent productReservedEvent = new ProductReservedEvent();
        BeanUtils.copyProperties(reserveProductCommand, productReservedEvent);

        AggregateLifecycle.apply(productReservedEvent);

    }

    @EventSourcingHandler
    public void on(ProductCreatedEvent productCreatedEvent) {
        this.productId = productCreatedEvent.getId();
        this.name = productCreatedEvent.getName();
        this.price = productCreatedEvent.getPrice();
        this.qty = productCreatedEvent.getQuantity();
    }

    @EventSourcingHandler
    public void on(ProductReservedEvent productReservedEvent) {
        this.qty -= productReservedEvent.getQuantity();
    }
}

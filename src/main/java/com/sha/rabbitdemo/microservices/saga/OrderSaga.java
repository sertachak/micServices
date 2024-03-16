package com.sha.rabbitdemo.microservices.saga;

import com.sha.rabbitdemo.event.ProductCreatedEvent;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.spring.stereotype.Saga;

import javax.annotation.Nonnull;

@Saga
public class OrderSaga {

    private final transient CommandGateway commandGateway;

    public OrderSaga(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @StartSaga
    @SagaEventHandler(associationProperty = "id")
    public void handle(ProductCreatedEvent event) {
        ReserveProductCommand command = ReserveProductCommand.builder()
                .productId(event.getId())
                .quantity(event.getQuantity())
                .orderId(event.getId())
                .build();

        commandGateway.send(command, new CommandCallback<ReserveProductCommand, Object>() {
            @Override
            public void onResult(@Nonnull CommandMessage<? extends ReserveProductCommand> commandMessage, @Nonnull CommandResultMessage<?> commandResultMessage) {
                if (commandResultMessage.isExceptional()) {
                    //Start compensating transaction
                }
            }
        });
    }
}

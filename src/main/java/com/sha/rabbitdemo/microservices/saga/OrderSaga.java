package com.sha.rabbitdemo.microservices.saga;

import com.sha.rabbitdemo.event.ProductCreatedEvent;
import com.sha.rabbitdemo.event.ProductReservedEvent;
import com.sha.rabbitdemo.model.User;
import com.sha.rabbitdemo.query.FetchUserPaymentDetailsQuery;
import org.axonframework.commandhandling.CommandCallback;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.commandhandling.CommandResultMessage;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;

import javax.annotation.Nonnull;

@Saga
public class OrderSaga {

    private final transient CommandGateway commandGateway;
    private final transient QueryGateway queryGateway;

    public OrderSaga(CommandGateway commandGateway, QueryGateway queryGateway) {
        this.commandGateway = commandGateway;
        this.queryGateway = queryGateway;
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

    @SagaEventHandler(associationProperty = "orderId")
    public void handle(ProductReservedEvent event) {
        //Process user payment after handling event in processingGroup

        FetchUserPaymentDetailsQuery query = new FetchUserPaymentDetailsQuery();

        try {
            User user = queryGateway.query(query, ResponseTypes.instanceOf(User.class)).join();
            //Process user payment
        } catch (Exception e) {
            //Start compensating transaction
        }
    }
}

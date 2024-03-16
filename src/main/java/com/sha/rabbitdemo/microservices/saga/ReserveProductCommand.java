package com.sha.rabbitdemo.microservices.saga;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReserveProductCommand {

    @TargetAggregateIdentifier
    private String productId;
    private String orderId;
    private int quantity;
}

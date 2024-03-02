package com.sha.rabbitdemo.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

import java.math.BigDecimal;

@Data
@Builder
public class ProductCreateCommand {

    @TargetAggregateIdentifier
    private String id;
    private String name;
    private Double price;
    private Integer qty;
}

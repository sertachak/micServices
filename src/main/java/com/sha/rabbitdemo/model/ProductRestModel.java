package com.sha.rabbitdemo.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRestModel {

    private String name;
    private Double price;
    private Integer qty;
}

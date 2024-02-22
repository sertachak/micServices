package com.sha.rabbitdemo.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ProductRestModel {

    @NotBlank(message = "Product name is required")
    private String name;
    private Double price;
    private Integer qty;
}

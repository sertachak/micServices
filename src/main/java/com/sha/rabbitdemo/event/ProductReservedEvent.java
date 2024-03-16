package com.sha.rabbitdemo.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReservedEvent {

    private String productId;
    private String orderId;
    private int quantity;
}

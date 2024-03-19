package com.sha.rabbitdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class PaymentDetails {
    private final String name;
    private final String cardNumber;
    private final int validUntilMonth;
    private final int validUntilYear;
    private final String cvv;
}

package com.sha.rabbitdemo.reactor.mockData;

import com.sha.rabbitdemo.reactor.dto.Customer;

import java.util.List;

public enum MockData {
    INSTANCE;
    public List<Customer> getListOfCustomersMock() {
        return List.of(
                new Customer("1", "John"),
                new Customer("2", "Smith"),
                new Customer("3", "Sara"),
                new Customer("4", "Sam"),
                new Customer("5", "Tom")
        );
    }
}

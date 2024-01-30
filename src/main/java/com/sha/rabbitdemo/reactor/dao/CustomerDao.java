package com.sha.rabbitdemo.reactor.dao;

import com.sha.rabbitdemo.reactor.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class CustomerDao {


    public List<Customer> loadAllCustomersList() {
        return IntStream.range(1, 50)
                .peek(i -> System.out.println("Emitting " + i))
                .mapToObj(i -> new Customer(String.valueOf(i), "Object" + i))
                .toList();
    }

    public Flux<Customer> loadAllCustomersStream() {
        return Flux.range(1, 50)
                .doOnEach(i -> System.out.println("Emitting " + i.get()))
                .map(i -> new Customer(String.valueOf(i), "Object" + i))
                .doAfterTerminate(() -> System.out.println("Completed"));
    }
}

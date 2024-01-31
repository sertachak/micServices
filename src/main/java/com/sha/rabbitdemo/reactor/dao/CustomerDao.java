package com.sha.rabbitdemo.reactor.dao;

import com.sha.rabbitdemo.reactor.dto.Customer;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.stream.IntStream;

@Component
public class CustomerDao {

    public void sleepSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Customer> loadAllCustomersList() {
        return IntStream.range(1, 50)
                .peek(i -> System.out.println("Emitting " + i))
                .peek(i -> sleepSeconds(1))
                .mapToObj(i -> new Customer(String.valueOf(i), "Object" + i))
                .toList();
    }

    public Flux<Customer> loadAllCustomersStream() {
        return Flux.range(1, 10)
                .delayElements(java.time.Duration.ofSeconds(1))
                .doOnEach(i -> System.out.println("Emitting " + i.get()))
                .map(i -> new Customer(String.valueOf(i), "Object" + i))
                .doAfterTerminate(() -> System.out.println("Completed"));
    }
}

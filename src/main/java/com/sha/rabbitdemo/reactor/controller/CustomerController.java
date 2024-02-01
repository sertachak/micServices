package com.sha.rabbitdemo.reactor.controller;

import com.sha.rabbitdemo.reactor.dao.CustomerDao;
import com.sha.rabbitdemo.reactor.dto.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.function.Predicate;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerDao customerDao;
    private final Predicate<Customer> nameStartsWithS = customer -> customer.getName().startsWith("S");

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping(value = "/reactive", produces = "application/stream+json")
    public Flux<Customer> loadAllCustomers() {
        final Flux<Customer> customerFlux = customerDao.loadAllCustomersStream();

        final Flux<Customer> filtered = customerFlux.filter(element -> nameStartsWithS.negate().test(element));
        //filtered.subscribe().dispose(); take a look to this function
        return filtered;
    }
}

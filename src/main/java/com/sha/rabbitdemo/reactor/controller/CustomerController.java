package com.sha.rabbitdemo.reactor.controller;

import com.sha.rabbitdemo.reactor.dao.CustomerDao;
import com.sha.rabbitdemo.reactor.dto.Customer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    private final CustomerDao customerDao;

    public CustomerController(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @GetMapping(value = "/reactive", produces = "application/stream+json")
    public Flux<Customer> loadAllCustomers() {
        return customerDao.loadAllCustomersStream();
    }
}

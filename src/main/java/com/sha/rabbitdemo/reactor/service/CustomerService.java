package com.sha.rabbitdemo.reactor.service;

import com.sha.rabbitdemo.reactor.dao.CustomerDao;
import com.sha.rabbitdemo.reactor.dto.Customer;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.function.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerService {

    private CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public ServerResponse loadCustomersStream() {
        Flux<Customer> customerFlux = customerDao.loadAllCustomersStream();
        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(customerFlux);
    }
}

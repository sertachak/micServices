package com.sha.rabbitdemo.reactor;

import com.sha.rabbitdemo.reactor.service.CustomerService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;

@Configuration
public class RouterConfig {

    private final CustomerService customerService;

    public RouterConfig(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Bean
    RouterFunction<?> routerFunction() {
        return RouterFunctions.route()
                .GET("/customer/reactive", request -> customerService.loadCustomersStream())
                .build();
    }
}

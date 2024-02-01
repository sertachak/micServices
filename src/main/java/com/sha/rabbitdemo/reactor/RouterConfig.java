package com.sha.rabbitdemo.reactor;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;

@Configuration
public class RouterConfig {

    RouterFunction<?> routerFunction() {
        return RouterFunctions.route()
                .GET("/customer/reactive", request -> null)
                .build();
    }
}

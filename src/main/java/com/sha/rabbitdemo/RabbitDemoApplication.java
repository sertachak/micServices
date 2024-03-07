package com.sha.rabbitdemo;

import com.sha.rabbitdemo.command.CreateProductCommandInterceptor;
import com.sha.rabbitdemo.core.errorhandling.ProductServiceEventsErrorHandler;
import org.axonframework.commandhandling.CommandBus;
import org.axonframework.config.EventProcessingConfiguration;
import org.axonframework.config.EventProcessingConfigurer;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class RabbitDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(RabbitDemoApplication.class, args);
    }

    @Autowired
    public void registerCreateProductCommandMessageDispatchInterceptor(ApplicationContext context, CommandBus commandBus) {
        commandBus.registerDispatchInterceptor(context.getBean(CreateProductCommandInterceptor.class));
    }

    //this listener invocation errorHandler is used to handle the exceptions thrown by the event listeners
    @Autowired
    public void eventListenerInvocationErrorHandler(EventProcessingConfigurer eventProcessingConfigurer) {
        eventProcessingConfigurer
                .registerListenerInvocationErrorHandler("product-group",
                        config -> new ProductServiceEventsErrorHandler());
    }

}

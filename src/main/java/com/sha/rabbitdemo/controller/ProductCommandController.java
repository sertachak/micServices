package com.sha.rabbitdemo.controller;

import com.sha.rabbitdemo.command.ProductCreateCommand;
import com.sha.rabbitdemo.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("product/api/v1")
public class ProductCommandController {

    private Logger logger = LoggerFactory.getLogger(ProductCommandController.class);

    private Environment environment;

    private CommandGateway commandGateway;

    public ProductCommandController(Environment environment, CommandGateway commandGateway) {
        this.environment = environment;
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addProduct(@RequestBody ProductRestModel productRestModel) throws ExecutionException, InterruptedException {
        ProductCreateCommand  productCreateCommand = ProductCreateCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .qty(productRestModel.getQty())
                .build();

        final String localPort = environment.getProperty("local.server.port");
        logger.info("The message got by environment is: {}", localPort);

        final CompletableFuture<Object> product = commandGateway.send(productCreateCommand);

        return product.get().toString();
    }

}

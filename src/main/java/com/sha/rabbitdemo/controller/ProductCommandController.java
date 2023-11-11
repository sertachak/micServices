package com.sha.rabbitdemo.controller;

import com.sha.rabbitdemo.command.ProductCreateCommand;
import com.sha.rabbitdemo.model.ProductRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("fruit/api/v1")
public class ProductCommandController {

    private CommandGateway commandGateway;

    public ProductCommandController(CommandGateway commandGateway) {
        this.commandGateway = commandGateway;
    }

    @PostMapping
    public String addProduct(@RequestBody ProductRestModel productRestModel) {
        ProductCreateCommand  productCreateCommand = ProductCreateCommand.builder()
                .productId(UUID.randomUUID().toString())
                .name(productRestModel.getName())
                .price(productRestModel.getPrice())
                .qty(productRestModel.getQty())
                .build();

        return commandGateway.sendAndWait(productCreateCommand);
    }

}

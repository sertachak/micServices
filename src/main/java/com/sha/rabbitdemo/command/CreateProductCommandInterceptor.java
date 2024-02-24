package com.sha.rabbitdemo.command;

import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {
    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (command.getPayload() instanceof ProductCreateCommand) {
                ProductCreateCommand productCreateCommand = (ProductCreateCommand) command.getPayload();
                if (productCreateCommand.getPrice() < 0) {
                    throw new IllegalArgumentException("Price cannot be negative");
                }
            }
            return command;
        };
    }
}

package com.sha.rabbitdemo.command;

import com.sha.rabbitdemo.core.ProductLookupEntity;
import com.sha.rabbitdemo.core.ProductLookupRep;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.BiFunction;

@Component
public class CreateProductCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final ProductLookupRep productLookupRep;

    public CreateProductCommandInterceptor(ProductLookupRep productLookupRep) {
        this.productLookupRep = productLookupRep;
    }

    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if (command.getPayload() instanceof ProductCreateCommand) {
                ProductCreateCommand productCreateCommand = (ProductCreateCommand) command.getPayload();
                if (productCreateCommand.getPrice() < 0) {
                    throw new IllegalArgumentException("Price cannot be negative");
                }
                final ProductLookupEntity byProductIdOrTitle = productLookupRep.findByIdOrName(productCreateCommand.getId(), productCreateCommand.getName());

                if (byProductIdOrTitle != null) {
                    throw new IllegalArgumentException("Product with this id or name already exists");
                }
            }
            return command;
        };
    }
}

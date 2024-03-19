package com.sha.rabbitdemo.query;

import com.sha.rabbitdemo.model.User;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserQueryHandler {

    @QueryHandler
    public User handle(FetchUserPaymentDetailsQuery query) {
        return null;
    }
}

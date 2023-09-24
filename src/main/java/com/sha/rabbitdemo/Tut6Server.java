package com.sha.rabbitdemo;

import com.sha.rabbitdemo.enums.TutException;
import com.sha.rabbitdemo.exception.ApiException;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class Tut6Server {

    @RabbitListener(queues = "tut.rpc.requests")
    // @SendTo("tut.rpc.replies") used when the
    // client doesn't set replyTo.
    public int fibonacci(int asd) {
        int n = 5;
        System.out.println(" [x] Received request for " + n);
        int result = fib(n);
        System.out.println(" [.] Returned " + result);

        throw new ApiException(TutException.FILE_NOT_FOUND);
    }

    public int fib(int n) {
        return n == 0 ? 0 : n == 1 ? 1 : (fib(n - 1) + fib(n - 2));
    }

}
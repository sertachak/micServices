package com.sha.rabbitdemo;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;

public class Tut6Client {

    private RabbitTemplate template;
    private DirectExchange exchange;

    int start = 0;

    public Tut6Client(RabbitTemplate template, DirectExchange exchange) {
        System.out.println("Client is created");
        if(template == null)
            System.out.println("template is null");
        if(exchange == null)
            System.out.println("exchange is null");
        this.template = template;
        this.exchange = exchange;
    }

    @Scheduled(fixedDelay = 1000, initialDelay = 500)
    public void send() {
        System.out.println(" [x] Requesting fib(" + start + ")");
        Integer response = (Integer) template.convertSendAndReceive
                (exchange.getName(), "rpc", start++);
        System.out.println(" [.] Got '" + response + "'");
    }
}

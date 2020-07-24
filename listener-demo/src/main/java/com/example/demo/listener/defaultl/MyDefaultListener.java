package com.example.demo.listener.defaultl;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue("myDefaultQueue"))
public class MyDefaultListener {

    @RabbitHandler
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}
package com.example.demo.listener.defaultl;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queuesToDeclare = @Queue(value = "1"))
public class MyDefaultListener2 {

    @RabbitHandler
    public void onMessage(String msg) {
        System.out.println(msg);
    }
}

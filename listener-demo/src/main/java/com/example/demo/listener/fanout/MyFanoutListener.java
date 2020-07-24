package com.example.demo.listener.fanout;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.annotation.RabbitListeners;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RabbitListeners({
        @RabbitListener(bindings = @QueueBinding(value = @Queue("myFanoutQueue-one"),exchange = @Exchange(value = "myFanoutExchange", type = ExchangeTypes.FANOUT), key = "key.one")),
        @RabbitListener(bindings = @QueueBinding(value = @Queue("myFanoutQueue-two"),exchange = @Exchange(value = "myFanoutExchange", type = ExchangeTypes.FANOUT), key = "key.two")),
})
public class MyFanoutListener {

    @RabbitHandler
    public void onMessage(@Payload String msg, @Headers Map<String, Object> headers) {
        System.out.println("来自" + headers.get(AmqpHeaders.CONSUMER_QUEUE) + "的消息:" + msg);
    }
}
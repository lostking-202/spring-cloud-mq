package com.example.demo.controller;

import com.example.demo.service.MQServiceImpl;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Queue（队列）是RabbitMQ的内部对象，用于存储消息
 *
 * 生产者将消息发送到Exchange（交换器），由Exchange将消息路由到一个或多个Queue中（或者丢弃）。
 *
 * 生产者在将消息发送给Exchange的时候，一般会指定一个routing key，来指定这个消息的路由规则，而这个routing key需要与Exchange Type及binding key联合使用才能最终生效
 */
@RestController
public class MQController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    MQServiceImpl service;

    @GetMapping("/direct")
    public void directSend() {
        //(交换机,routingKey,消息内容)
        rabbitTemplate.convertAndSend("myDirectExchange","mine.direct","this is a message");
    }

    @GetMapping("/default")
    public void defaultSend() {
        //(队列,消息内容) 使用默认queue
        rabbitTemplate.convertAndSend("1","this is a message from default without routingKey");
        rabbitTemplate.convertAndSend("myDefaultQueue","this is a message from default");
    }

    @GetMapping("/fanout")
    // 广播
    public void fanoutSend() {
        //(交换机,routingKey,消息内容),routingKey随意
        rabbitTemplate.convertAndSend("myFanoutExchange","key.one","this is a message");
    }

    @GetMapping("/topic")
    public void topicSend() {
        //模拟某人在商店买彩票中奖了
        rabbitTemplate.convertAndSend("news-exchange","province.city.street.shop","有人中了大奖");
    }

    @GetMapping("/head")
    public void headSend() {
        rabbitTemplate.convertAndSend("myHeadExchange", "", "this is a message", message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setHeader("key-one", "1");
            return message;
        });
    }

    // 消息持久化是默认开启的
    @GetMapping("/nonPersisentSend")
    public void nonPersisentSend() {
        //(交换机,routingKey,消息内容)
        rabbitTemplate.convertAndSend("myDirectExchange", "mine.direct", "this is a message", new MessagePostProcessor() {
            @Override
            public Message postProcessMessage(Message message) throws AmqpException {
                message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.NON_PERSISTENT);
                return message;
            }
        });
    }

    @GetMapping("/ackTest")
    public void ackTest(String message) {
        //rabbitTemplate.convertAndSend("ackTest","ackTest",message);
        rabbitTemplate.convertAndSend("ackTest",message);
    }
}

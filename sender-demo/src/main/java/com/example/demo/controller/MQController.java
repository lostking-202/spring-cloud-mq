package com.example.demo.controller;

import com.example.demo.service.MQServiceImpl;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
        //(队列,消息内容)
        rabbitTemplate.convertAndSend("myDefaultQueue","this is a message");
    }

    @GetMapping("/fanout")
    public void fanoutSend() {
        //(交换机,routingKey,消息内容),routingKey随意
        rabbitTemplate.convertAndSend("myFanoutExchange","key.one","this is a message");
    }

    @GetMapping("/topic")
    public void topicSend() {
        //模拟某人在商店买彩票中奖了
        service.topicSend();
    }

    @GetMapping("/head")
    public void headSend() {
        rabbitTemplate.convertAndSend("myHeadExchange", "", "this is a message", message -> {
            MessageProperties properties = message.getMessageProperties();
            properties.setHeader("key-one", "1");
            return message;
        });
    }
}

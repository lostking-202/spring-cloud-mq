package com.example.demo.service;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQServiceImpl implements RabbitTemplate.ConfirmCallback{
    @Autowired
    RabbitTemplate rabbitTemplate;
    public void topicSend(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.convertAndSend("news-exchange","province.city.street.shop","有人中了大奖");
        int i=1/0;
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {

    }
}

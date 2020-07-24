package com.example.demo.service;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MQServiceImpl {
    @Autowired
    RabbitTemplate rabbitTemplate;
    @Transactional(rollbackFor=Exception.class)
    public void topicSend(){
        rabbitTemplate.convertAndSend("news-exchange","province.city.street.shop","有人中了大奖");
    }
}

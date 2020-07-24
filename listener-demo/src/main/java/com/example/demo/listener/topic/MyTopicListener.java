package com.example.demo.listener.topic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MyTopicListener {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("province-news-queue"),
            exchange = @Exchange(value = "news-exchange", type = ExchangeTypes.TOPIC),
            key = "province.#"))
    @RabbitHandler
    public void processHandler(String msg, Channel channel, Message message) throws IOException {
        try {
            System.out.println("来自省TV的消息:" + msg);
            //int i=1/0;
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (Exception e) {
            if (message.getMessageProperties().getRedelivered()) {
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("city-news-queue"),
            exchange = @Exchange(value = "news-exchange", type = ExchangeTypes.TOPIC),
            key = "province.city.#"))
    @RabbitHandler
    public void cityNews(String msg) {
        System.out.println("来自市TV的消息:" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue("street-news-queue"),
            exchange = @Exchange(value = "news-exchange", type = ExchangeTypes.TOPIC),
            key = "province.city.street.*"))
    @RabbitHandler
    public void streetNews(String msg) {
        System.out.println("来自街区TV的消息:" + msg);
    }
}
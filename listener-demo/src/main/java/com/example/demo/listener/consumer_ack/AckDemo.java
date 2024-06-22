package com.example.demo.listener.consumer_ack;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * ack 必须指定channel
 */
@Component
@RabbitListener(queuesToDeclare = @Queue(value = "ackTest"),ackMode = "MANUAL")
@Slf4j
public class AckDemo {

    @RabbitHandler
    public void processHandler(String msg, Channel channel, Message message) throws IOException {

        //multiple：是否批量确认，值为 true 则会一次性 ack所有小于当前消息 deliveryTag 的消息。
        try {
            if("message".equals(new String(message.getBody()))) {
                log.info("小富收到消息：{}", msg);
                System.out.println(message);
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            }else {
                //multiple：是否批量确认。
                //requeue：值为 true 消息将重新入队列。
                log.info("reject and delete");
                channel.basicReject(message.getMessageProperties().getDeliveryTag(),false);
            }
        }  catch (Exception e) {
            e.printStackTrace();
            if (message.getMessageProperties().getRedelivered()) {

                log.error("消息已重复处理失败,拒绝再次接收...");

                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false); // 拒绝消息
            } else {

                log.error("消息即将再次返回队列处理...");

                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
            }
        }
    }

}
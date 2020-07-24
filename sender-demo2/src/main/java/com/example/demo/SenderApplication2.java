package com.example.demo;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SenderApplication2 {

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication2.class, args);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);

		rabbitTemplate.setMandatory(true);
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {

		});
		/*// 消息确认, yml需要配置 publisher-confirms: true
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {

		});*/
		return rabbitTemplate;
	}

}

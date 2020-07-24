package com.example.demo;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class SenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		//开启事务
		rabbitTemplate.setChannelTransacted(true);
		return rabbitTemplate;
	}

	@Bean
	public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
		RabbitTransactionManager rabbitTransactionManager = new RabbitTransactionManager(connectionFactory);
		return rabbitTransactionManager;
	}
}

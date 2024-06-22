package com.example.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableTransactionManagement
@EnableSwagger2
@Slf4j
public class SenderApplication {

	public static void main(String[] args) {
		SpringApplication.run(SenderApplication.class, args);
	}

	@Bean
	public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
		RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		//开启事务
		rabbitTemplate.setChannelTransacted(true);
		// 设置mandatory为true，当找不到队列时，broker会调用basic.return方法将消息返还给生产者
		rabbitTemplate.setMandatory(true);

		// 成功失败到达exchange都会触发
		rabbitTemplate.setConfirmCallback((correlationData, ack, cause)->{
			if(ack){
				log.info("消息已经到达exchange");
			}else{
				log.info("消息没有到达exchange");
			}
			if(correlationData!=null){
				log.info("相关数据："+correlationData);
			}
			if(cause!=null){
				log.info("原因："+cause);
			}
		});

		// 只有没到达queue才会触发
		rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
			log.info("消息无法到达队列时触发");
			log.info("ReturnCallback:     " + "消息：" + message);
			log.info("ReturnCallback:     " + "回应码：" + replyCode);
			log.info("ReturnCallback:     " + "回应信息：" + replyText);
			log.info("ReturnCallback:     " + "交换机：" + exchange);
			log.info("ReturnCallback:     " + "路由键：" + routingKey);
		});

		return rabbitTemplate;
	}

	@Bean
	public RabbitTransactionManager rabbitTransactionManager(ConnectionFactory connectionFactory) {
		RabbitTransactionManager rabbitTransactionManager = new RabbitTransactionManager(connectionFactory);
		return rabbitTransactionManager;
	}

	@Bean
	public Connection getConnection(ConnectionFactory connectionFactory){
		return connectionFactory.createConnection();
	}
}

# spring-cloud-mq
spring-boot集成rabbitmq
包含常用的channel
事务消息
生产者confirm和returnback
消费者ack策略

消息队列持久化:
exchange:spring-boot-amqp默认开启持久化
如未开启可通过connection的相关api在声明exchange时开启(第三个参数)

    'channel.exchangeDeclare("amq.direct", "direct", false, true, null);'

也可以使用注解开启(自行查阅@Exchange注解)
    
Queue:spring-boot-amqp默认开启持久化
如未开启可通过connection的相关api在声明exchange时开启(第二个参数)

    'channel.queueDeclare("queue.persistent.name", true, false, false, null);'
    
也可以使用注解开启(自行查阅@Queue注解)

消息持久化:spring-boot-amqp默认开启消息持久化
如未开启可通过

    'basicPublish(String exchange, String routingKey, BasicProperties props, byte[] body)'
中的props设置delivery_mode=2即可

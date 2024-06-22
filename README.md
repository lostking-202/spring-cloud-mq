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

1. 丢失消息
   - 生产者丢失消息
      <p>callBack保证，消息没有成功到达exchange和queue都会触发</p>
   - 消息中间件丢失消息
      <p>exchange持久化，queue持久化，message持久化</p>
   - 消费者丢失消息
      ack机制，可以将消息退回queue或者重新消费或者记录到数据库
2. 重复消费
    - 如何防止重复消费
    <p>rabbit本身没有办法保证消息的重复消费，可以通过业务的幂等性保证，比如唯一的业务id</p>
3. rabbitmq可以用于分布式事务吗
4. rabbitmq常用的channel
5. rabbitmq常见的配置项
6. rabbitmq的事务有什么用
   <p>用来保证消息成功发送到消息中间件的</p>
package com.ys.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import com.ys.rabbitmq.config.amqp.RabbitMQConstant;
import com.ys.rabbitmq.config.amqp.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * 测试集群消息队列接受
 * ackMode = "manual" 开启确认消费模式
 */
@RabbitListener(queues = RabbitMQConstant.DIRECT_QUEUE_1)
@Component
public class HaConsume {
    private static final Logger log = LoggerFactory.getLogger(HaConsume.class);

    @RabbitHandler
    public void handle(@Payload User user, AmqpMessageHeaderAccessor messageHeaderAccessor, Channel channel) throws IOException {
        log.info("收到集群消息队列传递的消息：{}", user);
    }

}

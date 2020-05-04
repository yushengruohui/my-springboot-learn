package com.ys.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import com.ys.rabbitmq.config.amqp.RabbitMQConstant;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * ackMode = "manual" 开启确认消费模式
 */
@RabbitListener(queues = RabbitMQConstant.Headers.Queue.IMAGE, ackMode = "MANUAL")
@Component
public class TransactionConsume {

    @RabbitHandler
    public void handle(@Payload String message, AmqpMessageHeaderAccessor messageHeaderAccessor, Channel channel) throws IOException {
        System.out.println(message);
        /**
         * 单条消息确认
         */
        channel.basicAck(messageHeaderAccessor.getDeliveryTag(), false);
    }

    @RabbitHandler
    public void handle(@Payload byte[] bytes, AmqpMessageHeaderAccessor messageHeaderAccessor, Channel channel) throws IOException {
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        /**
         * 批量消息确认，将以前已经消费但未提交确认消息，全部确认
         */
        System.out.println(messageHeaderAccessor.getDeliveryTag());
        if (messageHeaderAccessor.getDeliveryTag() % 5 == 0) {
            try {
                //  channel.basicAck(messageHeaderAccessor.getDeliveryTag(),true);
            } catch (Exception e) {
                channel.basicNack(messageHeaderAccessor.getDeliveryTag(), true, true);
            }
        }
    }

}

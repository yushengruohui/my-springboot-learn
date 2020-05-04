package com.ys.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import com.ys.rabbitmq.config.amqp.RabbitMQConstant;
import com.ys.rabbitmq.config.amqp.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
@RabbitListener(queues = RabbitMQConstant.DIRECT_QUEUE_1, returnExceptions = "false", ackMode = "MANUAL")
public class RabbitMqConsume {
    private static final Logger log = LoggerFactory.getLogger(RabbitMqConsume.class);

    @RabbitHandler
    public void consumeData(
            /**
             * 接收当前连接的通道
             */
            Channel channel,
            /**
             * 接收消息对象（包含消息头、消息体、和相应的属性）
             */
            Message message,
            /**
             * 接收消息头和消息体
             */
            org.springframework.messaging.Message message1,
            /**
             * 接收消息头数据对象
             */
            org.springframework.messaging.MessageHeaders messageHeaders,
            /**
             * 接收可修改属性的基类
             */
            MessageHeaderAccessor messageHeaderAccessor,
            /**
             * 接收可修改AMQP规范源生属性
             */
            AmqpMessageHeaderAccessor amqpMessageHeaderAccessor,
            /**
             * 接收消息体
             */
            @Payload User user,
            /**
             * 接收消息头指定的属性
             */
            @Header(name = "amqp_deliveryTag") Long deliveryTag,
            /**
             * 接收消息头的所有属性
             */
            @Headers Map<String, Object> headers) throws IOException {
        //String msg = new String(payload);
        log.info("=====>" + Thread.currentThread().getName());
        log.info("监听" + RabbitMQConstant.DIRECT_QUEUE_1 + "队列==>" + user.toString());
        //channel.basicAck(amqpMessageHeaderAccessor.getDeliveryTag(),true);
    }


    @RabbitListener(
            bindings = @QueueBinding(
                    value = @Queue(value = "MRO.updateBaseInfo.queue", durable = "true"),
                    exchange = @Exchange(name = "MRO_Exchange", type = ExchangeTypes.DIRECT, durable = "true"),
                    key = "baseInfoUpdate"
            ),
            // errorHandler = "errorHandler",
            returnExceptions = "true",
            errorHandler = "testRabbitListenerErrorHandler"
    )
    public String receiveLocationChangeMessage(String message) {
        System.out.println(message);
        throw new RuntimeException("foo");
        // return "success";
    }


}

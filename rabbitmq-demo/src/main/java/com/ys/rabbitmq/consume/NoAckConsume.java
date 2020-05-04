package com.ys.rabbitmq.consume;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.messaging.handler.annotation.Payload;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;

//@Component
//@RabbitListener(queues = RabbitMqConfig.Headers.Queue.IMAGE)
public class NoAckConsume {

    @RabbitHandler
    public void handle(@Payload String message) {
        System.out.println(message);
    }

    @RabbitHandler
    public void handle(@Payload byte[] bytes) throws UnsupportedEncodingException {
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

}

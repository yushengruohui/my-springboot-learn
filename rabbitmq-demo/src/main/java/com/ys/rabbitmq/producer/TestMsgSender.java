package com.ys.rabbitmq.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

@Component
public class TestMsgSender {
    @Resource
    private AmqpTemplate rabbitTemplate;

    public void send() {
        String context = "time= " + new Date();
        System.out.println("Sender : " + context);

        this.rabbitTemplate.convertAndSend("testQueue", context);
    }
}

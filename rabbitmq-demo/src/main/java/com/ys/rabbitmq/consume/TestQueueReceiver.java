package com.ys.rabbitmq.consume;

import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RabbitListener(queues = "testQueue")
public class TestQueueReceiver {
    @RabbitHandler
    public void process(String test) {
        System.out.println("Receiver  : " + test);
    }
}

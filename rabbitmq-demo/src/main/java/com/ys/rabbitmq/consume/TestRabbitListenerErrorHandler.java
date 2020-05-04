package com.ys.rabbitmq.consume;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.RabbitListenerErrorHandler;
import org.springframework.amqp.rabbit.support.ListenerExecutionFailedException;
import org.springframework.stereotype.Component;

/**
 * @author luodea
 * @date 2020/2/4 13:25
 * @description
 */
@Component
public class TestRabbitListenerErrorHandler implements RabbitListenerErrorHandler {
    @Override
    public Object handleError(Message amqpMessage, org.springframework.messaging.Message<?> message, ListenerExecutionFailedException exception) throws Exception {
        return "TestRabbitListenerErrorHandler";
    }
}

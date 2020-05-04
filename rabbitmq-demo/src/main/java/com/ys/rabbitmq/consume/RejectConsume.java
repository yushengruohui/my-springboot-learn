package com.ys.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import com.ys.rabbitmq.config.amqp.User;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * 申明且监听队列basic.reject.queue，拒绝消息队列
 */
@RabbitListener(bindings = {
        @QueueBinding(
                value = @Queue(value = "basic.reject.queue", arguments = {@Argument(name = "x-expires", value = "5000", type = "java.lang.Integer")}),
                exchange = @Exchange(value = "rejectExchange"),
                key = "basic.reject.queue"
        )}, ackMode = "MANUAL")
@Component
public class RejectConsume {


    @RabbitHandler
    public void handle(@Payload User message, AmqpMessageHeaderAccessor messageHeaderAccessor, Channel channel) throws IOException {
        System.out.println(message);
        /**
         * 拒绝消费消息
         * basicNack(long deliveryTag, boolean multiple, boolean requeue)
         * deliveryTag 接收标签
         *  requeue true将消息回滚至队列，false：将消息设为死亡或移动至死信队列
         */
        channel.basicReject(messageHeaderAccessor.getDeliveryTag(), true);

    }

    @RabbitHandler
    public void handle(@Payload byte[] bytes, AmqpMessageHeaderAccessor messageHeaderAccessor, Channel channel) throws IOException {
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
    }

}

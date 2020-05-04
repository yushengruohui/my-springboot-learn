package com.ys.rabbitmq.consume;

import com.rabbitmq.client.Channel;
import com.ys.rabbitmq.config.amqp.RabbitMQConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpMessageHeaderAccessor;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;


/**
 * ackMode = "MANUAL"， 开启手动确认消费模式，接受信息后不发送确认信息给消息队列服务器，消息队列服务器将再次发送信息过来，直到确认为止。
 * ackMode = "AUTO"，自动确认消费模式，消息队列服务器发送信息过来后，不管消费者是否接受，都当作接受处理。
 */
@RabbitListener(queues = RabbitMQConstant.Headers.Queue.IMAGE, ackMode = "MANUAL")
@Component
public class AckConsume {
    private static final Logger log = LoggerFactory.getLogger(AckConsume.class);

    /**
     * 处理消息体为 String类型 的消息。
     *
     * @param message               接受的消息
     * @param messageHeaderAccessor 信息头存储器
     * @param channel               管道
     * @throws IOException
     */
    @RabbitHandler
    public void handle(@Payload String message, AmqpMessageHeaderAccessor messageHeaderAccessor, Channel channel) throws IOException {
        log.info("ack[MANUAL]接受到String类型 的信息为：{}", message);
        try {
            /*
             * 确认获取信息，将通过管道回复确认消费给消息队列服务器
             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
             * deliveryTag 接收标签
             * multiple 是否批量回滚
             */
            channel.basicAck(messageHeaderAccessor.getDeliveryTag(), false);

        } catch (Exception e) {
            log.error("ack[MANUAL]接受消息异常");
            /*
             * 有异常，连接中断或者抖动，告诉消息队列服务器，申请重新发送消息。
             * 回滚确定
             * basicNack(long deliveryTag, boolean multiple, boolean requeue)
             * deliveryTag 接收标签
             * multiple 是否批量回滚
             * requeue true:将消息回滚至队列 || false:将消息设为死亡或移动至死信队列
             */
            channel.basicNack(messageHeaderAccessor.getDeliveryTag(), false, true);
        }
    }

    /**
     * 处理消息体为 byte[]类型 的消息。
     * 默认情况下，对象会被转换成byte[]，可以在这里反序列化。
     *
     * @param bytes                 对象转换成的二级制数组
     * @param messageHeaderAccessor 信息头存储器
     * @param channel               通信管道
     * @throws IOException
     */
    @RabbitHandler
    public void handle(@Payload byte[] bytes, AmqpMessageHeaderAccessor messageHeaderAccessor, Channel channel) throws IOException {
        System.out.println(new String(bytes, StandardCharsets.UTF_8));
        //批量消息确认，将以前已经消费但未提交确认消息，全部确认
        channel.basicAck(messageHeaderAccessor.getDeliveryTag(), true);
    }

}

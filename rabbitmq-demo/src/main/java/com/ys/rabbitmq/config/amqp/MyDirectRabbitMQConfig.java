package com.ys.rabbitmq.config.amqp;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 简单RabbitMQ配置
 */
@Configuration
public class MyDirectRabbitMQConfig {

    @Bean
    public Queue myDirectQueue() {
        /**
         * 设置队列特殊参数
         *  x-message-ttl 强制设置队列消息的最大生存时间 （所有消息）
         *  x-expires 队列空闲一段时间删除其对此队列
         *  x-max-length 队列的最大长度，如果队列大于这个值删除最老的消息（可能移到死信队列）
         *  x-dead-letter-exchange  死信交换器
         *  x-dead-letter-routing-key   死信交换器路由的键
         *  x-max-priority  设置队列的最大优先级
         *  x-ha-policy 创建HA队列时，指定模式为nodes
         *  x-ha-nodes HA所在集群的节点是一个数组
         */
        Map<String, Object> arguments = new HashMap<>(4);
        /**
         * Queue(String name, boolean durable, boolean exclusive, boolean autoDelete，arguments)
         * name 队列名称
         * durable 是否持久化队列
         * exclusive 是否为独占队列（只允许单个消费者）
         * autoDelete 是否自定删除队列，（当没有消费者者时自动删除）
         * arguments 申明参数
         */
        return new Queue("test_direct_queue", true, false, false, arguments);
    }
}

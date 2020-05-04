package com.ys.rabbitmq.config.amqp;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.transaction.RabbitTransactionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yusheng
 * Created on 2020-05-01 22:51
 **/
@Configuration
public class MyRabbitMQConfig {
    @Bean
    public Queue directHaQueue() {
        /**
         * 设置参数
         */
        Map<String, Object> arguments = new HashMap<>(4);
        /**
         * 该队列所在集群的节点列表
         */
        String[] nodes = new String[]{"rabbit@rabbit3", "rabbit@rabbit4", "rabbit@rabbit5"};
        /**
         * 启用高可用队列
         */
        arguments.put("x-ha-policy", "nodes");
        /**
         * 设置队列所在集群中的节点
         */
        arguments.put("x-ha-nodes", nodes);
        /**
         *  Queue(队列名称,是否持久化队列,是否为独占队列,是否自动删除,申明参数)
         */
        return new Queue(RabbitMQConstant.DIRECT_QUEUE_1, true, false, false, arguments);
    }

    @Bean
    public Queue directQueue2() {
        return new Queue(RabbitMQConstant.DIRECT_QUEUE_2);
    }

    @Bean
    public Queue topicProvinceQueue() {
        return new Queue(RabbitMQConstant.Topic.Queue.PROVINCE);
    }

    @Bean
    public Queue topicCityQueue() {
        return new Queue(RabbitMQConstant.Topic.Queue.CITY);
    }

    @Bean
    public Queue topicCountyQueue() {
        return new Queue(RabbitMQConstant.Topic.Queue.COUNTY);
    }


    @Bean
    public Queue alternateQueue() {
        return new Queue(RabbitMQConstant.ALTERNATE_QUEUE);
    }

    @Bean
    public Exchange directExchange() {
        /**
         * 申明交换器时需要设置的参数
         */
        Map<String, Object> arguments = new HashMap<>(4);
        /**
         * 为当前交换器设置备用交换器
         * RabbitMQConstant.ALTERNATE_EXCHANGE 当前可用的交换器名称
         */
        arguments.put("alternate-exchange", RabbitMQConstant.ALTERNATE_EXCHANGE);
        return new DirectExchange(RabbitMQConstant.DIRECT_EXCHANGE, true, false, arguments);
    }

    @Bean
    public FanoutExchange alternateExchange() {
        Map<String, Object> arguments = new HashMap<>(4);
        arguments.put("alternate-exchange", RabbitMQConstant.ALTERNATE_EXCHANGE);
        return new FanoutExchange(RabbitMQConstant.ALTERNATE_EXCHANGE, true, false, arguments);
    }


    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(RabbitMQConstant.Topic.Exchange.DEFAULT);
    }

    @Bean
    public Binding topicProvinceBinding() {
        /**
         * 将RabbitMqConfig.Topic.Queue.PROVINCE和RabbitMqConfig.Topic.Exchange.DEFAULT进行绑定
         * 绑定规则RabbitMqConfig.Topic.RouteKey.PROVINCE 贵州省.# 交换器会将以“贵阳省”开头的所有路由键路由到PROVINCE队列
         * 如 贵州省..,贵州省.XXX.XXX 等
         */
        return BindingBuilder.bind(topicProvinceQueue()).to(topicExchange()).with(RabbitMQConstant.Topic.RouteKey.PROVINCE);
    }

    @Bean
    public Binding topicCityBinding() {
        /**
         * 将RabbitMqConfig.Topic.Queue.CITY和RabbitMqConfig.Topic.Exchange.DEFAULT进行绑定
         * 绑定规则RabbitMqConfig.Topic.RouteKey.PROVINCE 贵州省.贵阳市.# 交换器会将以“贵州省.贵阳市”开头的所有路由键的消息路由到CITY队列
         * 如 贵州省.贵阳市.XXX,贵州省.贵阳市.等
         */
        return BindingBuilder.bind(topicCityQueue()).to(topicExchange()).with(RabbitMQConstant.Topic.RouteKey.CITY);
    }

    @Bean
    public Binding topicCountyBinding() {
        /**
         * 将RabbitMqConfig.Topic.Queue.COUNTY和RabbitMqConfig.Topic.Exchange.DEFAULT进行绑定
         * 绑定规则RabbitMqConfig.Topic.RouteKey.PROVINCE 贵州省.*.花果园 交换器会将以“贵州省.”开头且以“花果园”结尾的所有路由键路由到COUNTY队列
         * 如 贵州省..花果园,贵州省.XXX.花果园等
         */
        return BindingBuilder.bind(topicCountyQueue()).to(topicExchange()).with(RabbitMQConstant.Topic.RouteKey.COUNTY);
    }


    @Bean
    public Binding alternateBinding() {
        return BindingBuilder.bind(alternateQueue()).to(alternateExchange());
    }

    @Bean
    public Binding directBindCard() {
        return BindingBuilder
                /**
                 * 绑定的队列
                 */
                .bind(directHaQueue())
                /**
                 * 绑定交换器
                 */
                .to(directExchange())
                /**
                 * 交换器路由到队列的路由键
                 */
                .with(RabbitMQConstant.DIRECT_ROUTE_KEY_CARD)
                /**
                 * 绑定时设置的参数
                 */
                .noargs();
    }

    @Bean
    public Binding directBindPhone() {
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with(RabbitMQConstant.DIRECT_ROUTE_KEY_PHONE).noargs();
    }


    @Bean
    public Queue headersImageQueue() {
        return new Queue(RabbitMQConstant.Headers.Queue.IMAGE);
    }

    @Bean
    public Queue headersCarQueue() {
        return new Queue(RabbitMQConstant.Headers.Queue.CAR);
    }

    @Bean
    public HeadersExchange headersExchange() {
        return new HeadersExchange(RabbitMQConstant.Headers.Exchange.DEFAULT);
    }

    @Bean
    public Binding headersImagesBinding() {
        /**
         * 采用headers参数匹配的方式路由
         * whereAny(headers) 匹配headers参数中一个或多个（x-match=any）
         * RabbitMQConstant.Headers.RouteKey.IMAGES 定义匹配的参数
         */
        return BindingBuilder.bind(headersImageQueue()).to(headersExchange()).whereAny(RabbitMQConstant.Headers.RouteKey.IMAGES).match();
    }

    @Bean
    public Binding headersCarBinding() {
        /**
         * 采用headers参数匹配的方式路由
         * whereAll(headers) 匹配headers的所有参数，必须都成功才可路由（x-match=all）
         * RabbitMQConstant.Headers.RouteKey.IMAGES 定义匹配的参数
         */
        return BindingBuilder.bind(headersCarQueue()).to(headersExchange()).whereAll(RabbitMQConstant.Headers.RouteKey.CAR).match();
    }


    /**
     * 配置启用rabbitmq事务
     *
     * @param connectionFactory
     * @return
     */
    @Bean("rabbitTransactionManager")
    public RabbitTransactionManager rabbitTransactionManager(CachingConnectionFactory connectionFactory) {
        return new RabbitTransactionManager(connectionFactory);
    }
}

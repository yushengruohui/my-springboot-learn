package com.ys.rabbitmq.producer;

import com.ys.rabbitmq.config.amqp.RabbitMQConstant;
import com.ys.rabbitmq.config.amqp.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;

/**
 * 生产者
 */
@RestController
public class ProducerController {
    private static final Logger log = LoggerFactory.getLogger(ProducerController.class);

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @GetMapping("/publish")
    public String publish() {
        User user = getUser();
        /**
         * RabbitMqConfig.DIRECT_EXCHANGE 交换器名称,和已申明的必须一致
         * RabbitMqConfig.DIRECT_ROUTE_KEY_CARD 路由键,交换器通过该路由键将消息路由到相应的队列
         * user 消息数据
         */
        rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_EXCHANGE, RabbitMQConstant.DIRECT_ROUTE_KEY_CARD, user);
        log.info(user.toString());
        return "ok";
    }


    @GetMapping("/mtdPublish")
    public String mtdPublish() {
        User user = getUser();
        /**
         * 设置发布时监听Basic.Return命令方法
         * message1 消息内容
         * replyCode 恢复码
         * replyText 回复描述
         * exchange 交换器名称
         * routingKey 路由键名称
         */
        rabbitTemplate.setReturnCallback((message1, replyCode, replyText, exchange, routingKey) -> {
            log.info("message:{}; replyCode: {}; replyText: {} ; exchange:{} ; routingKey:{}",
                    message1, replyCode, replyText, exchange, routingKey);
        });
        /**
         * 设置mandatory为true,如果为true当发布消息不可路由时rabbitmq会返回Basic.Return命令
         */
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_EXCHANGE, RabbitMQConstant.DIRECT_ROUTE_KEY_PHONE, user);
        log.info(user.toString());
        return "ok";
    }


    @GetMapping("/confirmPublish")
    public String confirmPublish() {
        User user = getUser();
        /**
         * 确认方式发布消息
         * correlationData 消息关联对象（和发布消息相关联的对象）
         * ack 如果为true表示消息已送达交换器,否则本次发布有异常
         * cause 发布失败时返回原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            log.info("correlationData:{}; ack: {}; cause: {} ;",
                    correlationData, ack, cause);
        });
        rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_EXCHANGE, RabbitMQConstant.DIRECT_ROUTE_KEY_CARD, user, new CorrelationData(user.getId()));
        log.info(user.toString());
        return "ok";
    }


    @GetMapping("/transactionPublish")
    @Transactional
    public String transactionPublish() {
        rabbitTemplate.setChannelTransacted(true);
        /**
         * 设置发布时监听Basic.Return命令方法
         * message1 消息内容
         * replyCode 恢复码
         * replyText 回复描述
         * exchange 交换器名称
         * routingKey 路由键名称
         */
        rabbitTemplate.setReturnCallback((message1, replyCode, replyText, exchange, routingKey) -> {
            log.info("message:{}; replyCode: {}; replyText: {} ; exchange:{} ; routingKey:{}",
                    message1, replyCode, replyText, exchange, routingKey);
        });
        User user = getUser();
        rabbitTemplate.convertAndSend(RabbitMQConstant.DIRECT_EXCHANGE, RabbitMQConstant.DIRECT_ROUTE_KEY_PHONE, user, new CorrelationData(user.getId()));
        System.out.println("发布完成");
        return "事物发布完成";
    }


    private User getUser() {
        Random random = new Random();
        return User.builder()
                .id("52272819881109631" + random.nextInt(10))
                .name("test" + random.nextInt(100))
                .age(random.nextInt(100))
                .build();
    }


}

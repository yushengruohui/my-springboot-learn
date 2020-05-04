package com.ys.rabbitmq;

import com.ys.rabbitmq.producer.TestMsgSender;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class RabbitmqDemoApplicationTests {

    @Resource
    private TestMsgSender testMsgSender;

    @Test
    void contextLoads() {
        testMsgSender.send();
    }

}

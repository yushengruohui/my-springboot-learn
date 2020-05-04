package com.ys.rabbitmq.config.amqp;

import java.util.HashMap;
import java.util.Map;

/**
 * 约束类，配置常量类
 */
public class RabbitMQConstant {
    /**
     * 高可用DIRECT队列
     */
    public static final String DIRECT_QUEUE_1 = "DIRECT_QUEUE_1";
    public static final String DIRECT_QUEUE_2 = "DIRECT_QUEUE_2";
    public static final String DIRECT_EXCHANGE = "DIRECT_EXCHANGE";
    public static final String DIRECT_ROUTE_KEY_CARD = "DIRECT_ROUTE_KEY_CARD";
    public static final String DIRECT_ROUTE_KEY_PHONE = "DIRECT_ROUTE_KEY_PHONE";

    /**
     * 备用交换器名称
     */
    public static final String ALTERNATE_EXCHANGE = "ALTERNATE_EXCHANGE_123213";
    /**
     * 备用队列
     */
    public static final String ALTERNATE_QUEUE = "ALTERNATE_QUEUE";


    /***************************************定义topic类型参数 开始******************************************************/
    public static class Topic {
        /**
         * 队列类型
         */
        public static class Queue {
            public static final String PROVINCE = "TOPIC_QUEUE_PROVINCE";
            public static final String CITY = "TOPIC_QUEUE_CITY";
            public static final String COUNTY = "TOPIC_QUEUE_COUNTY";
        }

        /**
         * 交换器类型
         */
        public static class Exchange {
            public static final String DEFAULT = "TOPIC_EXCHANGE";
        }

        /**
         * 路由键
         * 格式 PROVINCE.CITY.COUNTY
         */
        public static class RouteKey {
            public static final String PROVINCE = "贵州省.#";
            public static final String CITY = "贵州省.贵阳市.#";
            public static final String COUNTY = "贵州省.*.花果园";

            public static String generationKey(String province) {
                return generationKey(province, null, null);
            }

            public static String generationKey(String province, String city) {
                return generationKey(province, city, null);
            }

            public static String generationKey(String province, String city, String county) {
                return (province == null ? "" : province) + "." + (city == null ? "" : city) + "." + (county == null ? "" : county);
            }
        }

    }


    /***************************************定义Headers类型参数 开始******************************************************/
    public static class Headers {
        /**
         * 队列类型
         */
        public static class Queue {
            /**
             * 图片队列
             */
            public static final String IMAGE = "HEADERS_QUEUE_IMAGE";
            /**
             * 车队列
             */
            public static final String CAR = "HEADERS_QUEUE_CAR";
        }

        /**
         * 交换器类型
         */
        public static class Exchange {
            public static final String DEFAULT = "HEADERS_EXCHANGE";
        }

        /**
         * 路由键
         * 格式 PROVINCE.CITY.COUNTY
         */
        public static class RouteKey {
            /**
             * IMAGE绑定配置值
             */
            public static final Map<String, Object> IMAGES = new HashMap<>();

            /**
             * CAR绑定配置值
             */
            public static final Map<String, Object> CAR = new HashMap<>();

            static {
                IMAGES.put("suffix1", ".jpg");
                IMAGES.put("suffix2", ".png");
                IMAGES.put("suffix3", ".gif");

                CAR.put("condition1", "ok");
                CAR.put("condition2", "ok");
                CAR.put("condition3", "ok");
            }


        }

    }

}

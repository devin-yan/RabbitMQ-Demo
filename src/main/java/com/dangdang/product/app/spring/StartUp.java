package com.dangdang.product.app.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by devin_yan on 16/11/23.
 */
public class StartUp {


    public static void main(String[] args) throws InterruptedException {
        final AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-config.xml");

        AmqpTemplate amqpTemplate = (AmqpTemplate) context.getBean("rabbitTemplate");

        amqpTemplate.convertAndSend("spring_queue_key", "hello world!");

        amqpTemplate.convertAndSend("spring_queue_key", "hello world!");

        context.close();
    }

}

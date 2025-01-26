package com.example.productservice.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String STOCK_CHECK_QUEUE   = "stock-check-queue";
    public static final String STOCK_CHECKED_QUEUE = "stock-checked-queue";

    @Bean
    public Queue stockCheckQueue() {
        return new Queue(STOCK_CHECK_QUEUE, false);
    }

    @Bean
    public Queue stockCheckedQueue() {
        return new Queue(STOCK_CHECKED_QUEUE, false);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }


}

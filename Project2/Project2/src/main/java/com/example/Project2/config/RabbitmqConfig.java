package com.example.Project2.config;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitmqConfig {
    public static final String QUEUE = "order-queue";

    @Bean
    public  Queue queue() {
        return new Queue(QUEUE, true);
    }
}

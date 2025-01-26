package com.example.orderservice.messaging;

import com.example.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.example.orderservice.config.RabbitMQConfig.STOCK_CHECKED_QUEUE;
@Slf4j
@Component
public class StockCheckedListener {

    private final OrderService orderService;

    public StockCheckedListener(OrderService orderService) {
        this.orderService = orderService;
    }

    @RabbitListener(queues = STOCK_CHECKED_QUEUE)
    public void handleStockChecked(StockCheckedMessage message) {
        log.info("Order finalized for correlationId: " + message.getCorrelationId());

        orderService.finalizeOrder(message.getCorrelationId(), message.isSufficient());
        log.info("Order finalized for correlationId: " + message.getCorrelationId());
    }
}

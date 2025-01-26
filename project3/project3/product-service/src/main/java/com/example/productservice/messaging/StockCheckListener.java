package com.example.productservice.messaging;

import com.example.productservice.config.RabbitMQConfig;
import com.example.productservice.model.Product;
import com.example.productservice.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class StockCheckListener {

    private final ProductService productService;
    private final AmqpTemplate amqpTemplate;

    public StockCheckListener(ProductService productService, AmqpTemplate amqpTemplate) {
        this.productService = productService;
        this.amqpTemplate = amqpTemplate;
    }

    @RabbitListener(queues = RabbitMQConfig.STOCK_CHECK_QUEUE)
    public void handleCheckStock(CheckStockMessage request) {

        log.info("Received stock check request: {}", request);
        log.info("Checking stock for product ID: {}", request.getProductId());
        boolean isSufficient = false;
        Product product = null;

        try {
            product = productService.getProductById(request.getProductId());
        } catch (Exception ex) {
            log.warn("Product not found for ID: {}. Error: {}", request.getProductId(), ex.getMessage());
        }

        if (product != null) {
            int currentStock = product.getStock();
            isSufficient = currentStock >= request.getRequestedQuantity();

            if (isSufficient) {
                productService.updateStock(request.getProductId(), -request.getRequestedQuantity());
            }
        }
        log.info("Stock check result: {}", isSufficient);
        StockCheckedMessage response = new StockCheckedMessage();
        response.setCorrelationId(request.getCorrelationId());
        response.setProductId(request.getProductId());
        response.setRequestedQuantity(request.getRequestedQuantity());
        response.setSufficient(isSufficient);

        amqpTemplate.convertAndSend(
                RabbitMQConfig.STOCK_CHECKED_QUEUE,
                response
        );

        log.info("Stock check response sent: {}", response);

    }
}

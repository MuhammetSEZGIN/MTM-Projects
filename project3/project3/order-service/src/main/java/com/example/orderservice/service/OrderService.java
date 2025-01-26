package com.example.orderservice.service;

import com.example.orderservice.config.RabbitMQConfig;
import com.example.orderservice.messaging.CheckStockMessage;
import com.example.orderservice.model.Order;
import com.example.orderservice.repository.OrderRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final AmqpTemplate amqpTemplate;

    public OrderService(OrderRepository orderRepository, AmqpTemplate amqpTemplate) {
        this.orderRepository = orderRepository;
        this.amqpTemplate = amqpTemplate;
    }
    public java.util.List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order createOrder(Order order) {
        // 1) Siparişi "PENDING_STOCK" durumuyla kaydedelim
        order.setStatus("PENDING_STOCK");
        order.setOrderDate(new Date());

        // correlationId oluştur (örnek: UUID) ya da orderId'yi kullan
        String correlationId = UUID.randomUUID().toString();
        order.setCorrelationId(correlationId);

        // DB'ye kaydet
        Order savedOrder = orderRepository.save(order);

        // 2) RabbitMQ'ya stok kontrol isteği (CheckStockMessage) gönder
        CheckStockMessage checkMsg = new CheckStockMessage();
        checkMsg.setCorrelationId(correlationId);
        checkMsg.setProductId(savedOrder.getProductId());
        checkMsg.setRequestedQuantity(savedOrder.getQuantity());

        amqpTemplate.convertAndSend(
                RabbitMQConfig.STOCK_CHECK_QUEUE,
                checkMsg
        );
        log.info("Stock check request sent for correlationId: " + correlationId);
        // PENDING_STOCK durumundaki siparişi döndür.
        // Stok yeterli/yetersiz yanıtı "StockCheckedMessage" gelince işlenecek
        return savedOrder;
    }

    /**
     * "StockChecked" mesajı geldiğinde çağrılacak metod:
     * sufficient = true => CREATED, false => REJECTED
     */
    public void finalizeOrder(String correlationId, boolean sufficient) {
        Optional<Order> optionalOrder = orderRepository.findByCorrelationId(correlationId);
        if (optionalOrder.isEmpty()) {
            throw new RuntimeException("Order not found with correlationId: " + correlationId);
        }

        Order order = optionalOrder.get();
        if (!sufficient) {
            order.setStatus("REJECTED");
        } else {
            order.setStatus("CREATED");
        }
        orderRepository.save(order);
    }

    public Order updateOrder(Order updatedOrder) {
        Order existing = orderRepository.findById(updatedOrder.getId())
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + updatedOrder.getId()));

        existing.setQuantity(updatedOrder.getQuantity());
        existing.setStatus(updatedOrder.getStatus());
        return orderRepository.save(existing);
    }

    public Order getOrderById(int orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found with ID: " + orderId));
    }

    public void deleteOrder(int id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID=" + id));
        orderRepository.delete(order);
    }


}

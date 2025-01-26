package com.example.Project2.consumer;
import com.example.Project2.dtos.OrderMessageDto;
import com.example.Project2.model.OrderStatus;
import com.example.Project2.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.example.Project2.config.RabbitmqConfig;

@Component
public class OrderConsumer {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Autowired
    public OrderConsumer(OrderService orderService, ObjectMapper objectMapper) {
        this.orderService = orderService;
        this.objectMapper = objectMapper;
    }

    /*
          Example message:
              {
                  "orderId": 5,
                  "orderStatus": "CONFIRMED"
              }
           */
    @RabbitListener(queues = RabbitmqConfig.QUEUE)
    public void handleOrderCreated(String message) {
        System.out.println("Consumer received: " + message);
        try {

            OrderMessageDto orderMessageDto = objectMapper.readValue(message, OrderMessageDto.class);

            if (orderMessageDto.getOrderId() != 0 && orderMessageDto.getOrderStatus() != null) {
                int orderId = orderMessageDto.getOrderId();
                OrderStatus orderStatus = orderMessageDto.getOrderStatus();
                orderService.updateOrderStatus(orderId, orderStatus);
                System.out.println("Order " + orderId + " status updated to " + orderStatus + ".");
            }

        } catch (Exception e) {
            throw new RuntimeException("Error parsing message", e);
        }
    }

}

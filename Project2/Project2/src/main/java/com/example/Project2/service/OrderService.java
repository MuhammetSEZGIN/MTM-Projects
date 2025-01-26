package com.example.Project2.service;

import com.example.Project2.dtos.OrderMessageDto;
import com.example.Project2.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.example.Project2.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.Project2.model.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.util.List;

@Service
public class OrderService  {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RabbitTemplate rabbitTemplate;


    @Autowired
    public OrderService(
            OrderRepository orderRepository,
            ProductRepository productRepository,
            RabbitTemplate rabbitTemplate) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }

    public Orders createOrder(Orders order) {
        Product product = productRepository.findById(order.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found with ID=" + order.getProductID()));
        if (product.getStock() < order.getQuantity()) {
            throw new RuntimeException("Not enough stock for product=" + product.getProductID());
        }
        product.setStock(product.getStock() - order.getQuantity());
        productRepository.save(product);

        Orders savedOrder = orderRepository.save(order);

        OrderMessageDto orderMessageDto = new OrderMessageDto();
        orderMessageDto.setOrderId(savedOrder.getOrderID());
        orderMessageDto.setOrderStatus(OrderStatus.valueOf(OrderStatus.PENDING.toString()));

        ObjectMapper objectMapper = new ObjectMapper();
        String message;
        try {
            message = objectMapper.writeValueAsString(orderMessageDto);
        } catch (Exception e) {
            throw new RuntimeException("Error converting order message to JSON", e);
        }        rabbitTemplate.convertAndSend("order-queue", message);
        return savedOrder;

    }
    public Orders updateOrderStatus(int id, OrderStatus status) {

        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID=" + id));
        order.setStatus(status);

        return orderRepository.save(order);
    }
    public Orders getOrderById(int id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID=" + id));
    }

    public Orders updateOrder(Orders order) {

        Orders existingOrder = orderRepository.findById(order.getOrderID()).orElseThrow(() ->
                new RuntimeException("Order not found with ID=" + order.getOrderID()));

        existingOrder.setStatus(order.getStatus());
        existingOrder.setProductID(order.getProductID());
        Product product = productRepository.findById(order.getProductID())
                .orElseThrow(() -> new RuntimeException("Product not found with ID=" + order.getProductID()));
        if (product.getStock() < order.getQuantity()) {
            throw new RuntimeException("Not enough stock for product=" + product.getProductID());
        }
        existingOrder.setQuantity(order.getQuantity());
        return orderRepository.save(existingOrder);

    }



    public void deleteOrder(int id) {
        Orders order = orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with ID=" + id));
        orderRepository.delete(order);
    }


}
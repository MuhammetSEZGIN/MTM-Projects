package com.example.orderservice.controller;

import com.example.orderservice.dtos.ResponseDto;
import com.example.orderservice.model.Order;
import com.example.orderservice.dtos.OrderRequestDto;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.util.Mapper;
import com.example.orderservice.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(@RequestBody OrderRequestDto orderDto) {
        try {
            Order order= new Order();
            order.setOrderDate(orderDto.getOrderDate());
            order.setProductId(orderDto.getProductId());
            order.setQuantity(orderDto.getQuantity());

            orderService.createOrder(order);
            return ResponseUtil.createSuccessResponse(order);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateOrder(@PathVariable int id, OrderRequestDto orderDto) {
        try {
            Order order = new Order();
            order.setId(id);
            Mapper.map(orderDto, order);
            orderService.updateOrder(order);
            return ResponseUtil.createSuccessResponse(orderDto);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDto> getAllOrders() {
        try {
            List<Order> orders = orderService.getAllOrders();
            return ResponseUtil.createSuccessResponse(orders);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deleteOrder(@PathVariable int id) {
        try {
            orderService.deleteOrder(id);
            return ResponseUtil.createSuccessResponse(null);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<ResponseDto> getOrderById(@PathVariable int id) {
        try {
            Order order = orderService.getOrderById(id);
            return ResponseUtil.createSuccessResponse(order);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }


}

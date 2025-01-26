// Project2/src/main/java/com/example/Project2/controller/OrderController.java
package com.example.Project2.controller;

import com.example.Project2.dtos.OrderRequestDto;
import com.example.Project2.dtos.ResponseDto;
import com.example.Project2.service.OrderService;
import com.example.Project2.util.Mapper;
import com.example.Project2.util.ResponseUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.Project2.dtos.OrderDto;
import com.example.Project2.model.Orders;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ResponseDto> createOrder(@RequestBody OrderRequestDto orderDto) {
        try {
            Orders order = new Orders();
            Mapper.map(orderDto, order);
            orderService.createOrder(order);
            return ResponseUtil.createSuccessResponse(orderDto);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDto> updateOrder(@PathVariable int id, OrderRequestDto orderDto) {
        try {
            Orders order = new Orders();
            order.setOrderID(id);
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
            List<Orders> orders = orderService.getAllOrders();
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
            Orders order = orderService.getOrderById(id);
            OrderDto orderDto = new OrderDto();
            Mapper.map(order, orderDto);
            return ResponseUtil.createSuccessResponse(orderDto);
        } catch (Exception e) {
            return ResponseUtil.createFailureResponse(e.getMessage());
        }
    }
}
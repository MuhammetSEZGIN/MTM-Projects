package com.example.Project2.controller;

import com.example.Project2.dtos.OrderRequestDto;
import com.example.Project2.dtos.ResponseDto;
import com.example.Project2.model.Orders;
import com.example.Project2.service.OrderService;
import com.example.Project2.util.ResponseUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class OrderControllerTest {

    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createOrder_success() {
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        Orders order = new Orders();
        when(orderService.createOrder(any(Orders.class))).thenReturn(order);

        ResponseEntity<ResponseDto> response = orderController.createOrder(orderRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        verify(orderService, times(1)).createOrder(any(Orders.class));
    }



    @Test
    void updateOrder_success() {
        int id = 1;
        OrderRequestDto orderRequestDto = new OrderRequestDto();
        Orders order = new Orders();
        when(orderService.updateOrder(any(Orders.class))).thenReturn(order);

        ResponseEntity<ResponseDto> response = orderController.updateOrder(id, orderRequestDto);

        assertEquals(200, response.getStatusCodeValue());
        verify(orderService, times(1)).updateOrder(any(Orders.class));
    }



    @Test
    void getAllOrders_success() {
        List<Orders> orders = Collections.singletonList(new Orders());
        when(orderService.getAllOrders()).thenReturn(orders);

        ResponseEntity<ResponseDto> response = orderController.getAllOrders();

        assertEquals(200, response.getStatusCodeValue());
        verify(orderService, times(1)).getAllOrders();
    }



    @Test
    void deleteOrder_success() {
        int id = 1;
        doNothing().when(orderService).deleteOrder(id);

        ResponseEntity<ResponseDto> response = orderController.deleteOrder(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(orderService, times(1)).deleteOrder(id);
    }



    @Test
    void getOrderById_success() {
        int id = 1;
        Orders order = new Orders();
        when(orderService.getOrderById(id)).thenReturn(order);

        ResponseEntity<ResponseDto> response = orderController.getOrderById(id);

        assertEquals(200, response.getStatusCodeValue());
        verify(orderService, times(1)).getOrderById(id);
    }


}
package com.example.Project2.dtos;

import com.example.Project2.model.OrderStatus;
import lombok.Data;

@Data
public class OrderMessageDto {
    private int orderId;
    private OrderStatus orderStatus;
}

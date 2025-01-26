package com.example.orderservice.dtos;

import lombok.Data;

import java.util.Date;

@Data
public class OrderDto {
    private int id;
    private int quantity;
    private Date orderDate;
    private String status;
    private int productId;

}

package com.example.Project2.dtos;

import com.example.Project2.model.OrderStatus;
import lombok.Data;
import java.util.Date;

@Data
public class OrderDto {
    private int orderID;
    private int quantity;
    private Date orderDate;
    private OrderStatus status;
    private int productID;

}

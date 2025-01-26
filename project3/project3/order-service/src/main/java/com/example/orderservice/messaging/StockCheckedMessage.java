package com.example.orderservice.messaging;

import lombok.Data;

import java.io.Serializable;

@Data
public class StockCheckedMessage implements Serializable {
    private String correlationId;
    private int productId;
    private int requestedQuantity;
    private boolean sufficient;

    // getters/setters
}

package com.example.productservice.messaging;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckStockMessage implements Serializable {
    private String correlationId;
    private int productId;
    private int requestedQuantity;
    // getters/setters
}

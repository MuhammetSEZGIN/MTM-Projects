package com.example.orderservice.messaging;

import lombok.Data;

import java.io.Serializable;

@Data
public class CheckStockMessage implements Serializable {
    private String correlationId;  // Siparişle eşleştirme için
    private int productId;
    private int requestedQuantity;

}

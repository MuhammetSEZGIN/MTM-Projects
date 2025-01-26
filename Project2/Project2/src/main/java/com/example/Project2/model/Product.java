package com.example.Project2.model;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int productID;
    private String productName;
    private double price;
    private Integer stock;
}

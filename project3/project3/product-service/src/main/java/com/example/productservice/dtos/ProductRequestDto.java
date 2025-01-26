package com.example.productservice.dtos;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequestDto {
    private String productName;
    @Min(value = 0, message = "Price must be greater than 0")
    private double price;
    @Min(value = 0, message = "Stock must be greater than 0")
    private Integer stock;

}

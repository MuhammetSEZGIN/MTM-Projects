package com.example.Project2.dtos;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    public int productID;
    public String productName;
    @Min(value = 0, message = "Price must be greater than 0")
    public double price;
    @Min(value = 0, message = "Stock must be greater than 0")
    public int stock;

}

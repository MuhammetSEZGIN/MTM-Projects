package com.example.productservice.dtos;

import jakarta.validation.constraints.Min;
import lombok.Data;
@Data
public class ProductDto {

    public int productID;
    public String productName;
    @Min(value = 0, message = "Price must be greater than 0")
    public double price;
    @Min(value = 0, message = "Stock must be greater than 0")
    public Integer stock;

}

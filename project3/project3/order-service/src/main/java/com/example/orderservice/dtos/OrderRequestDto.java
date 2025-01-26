package com.example.orderservice.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class OrderRequestDto {

    @Min(value = 1, message = "Quantity must be greater than 0")
    public int quantity;
    @FutureOrPresent(message = "Order date must be in the future or present")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    public Date orderDate;
    public String status;
    @NotNull(message = "Product ID is required")
    public int productId;
}


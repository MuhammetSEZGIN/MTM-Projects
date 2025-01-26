package com.example.productservice.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

public class ResponseDto {
    private String message;
    private int status;
    private Object data;

}
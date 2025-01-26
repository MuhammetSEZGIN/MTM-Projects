package com.example.Project2.util;

import com.example.Project2.dtos.ResponseDto;
import com.example.Project2.model.ResponseEnum;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {

    public static ResponseEntity<ResponseDto> createSuccessResponse(Object data) {
        ResponseDto responseDto = new ResponseDto(ResponseEnum.SUCCESS.getMessage(), HttpStatus.OK.value(), data);
        return ResponseEntity.ok(responseDto);
    }

    public static ResponseEntity<ResponseDto> createFailureResponse(String errorMessage) {
        ResponseDto responseDto = new ResponseDto(errorMessage, HttpStatus.BAD_REQUEST.value(), null);
        return ResponseEntity.badRequest().body(responseDto);
    }
}

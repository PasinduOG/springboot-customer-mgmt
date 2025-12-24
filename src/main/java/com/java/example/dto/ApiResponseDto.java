package com.java.example.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class ApiResponseDto {
    private String message;
    private boolean success;
    private Object data;

    public ApiResponseDto(String message, boolean success){
        this.message = message;
        this.success = success;
    }
}

package com.parkee.library.controllers;

import com.parkee.library.domains.models.BaseResponse;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvisor {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<BaseResponse<String>> exceptionHandler(Exception e){
        BaseResponse<String> response = new BaseResponse<>();
        response.setCode(500);
        response.setMessage("FAILED");
        response.setData(e.getMessage());
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(response.getCode()));
    }
}

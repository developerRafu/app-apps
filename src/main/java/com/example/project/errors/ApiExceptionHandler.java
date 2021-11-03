package com.example.project.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class ApiExceptionHandler {
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<StandartError> apiException(ApiException ex, HttpServletRequest res){
        return ResponseEntity.ok().body(new StandartError(ex.getMessage(), ex.getStatus().value(), System.currentTimeMillis()));
    }
    @ExceptionHandler(ServiceException.class)
    public ResponseEntity<StandartError> serviceException(ServiceException ex, HttpServletRequest res){
        return ResponseEntity.ok().body(new StandartError(ex.getMessage(), ex.getStatus().value(), System.currentTimeMillis()));
    }

}

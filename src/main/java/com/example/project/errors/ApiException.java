package com.example.project.errors;

import lombok.Getter;
import org.springframework.http.HttpStatus;

public class ApiException extends RuntimeException {
    private String msg;
    @Getter
    private HttpStatus status;
    public ApiException(String msg, HttpStatus status){
        super(msg);
        this.msg = msg;
        this.status = status;
    }
}

package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.TOO_MANY_REQUESTS)
public class RateLimitBlockedException extends RuntimeException {
    public RateLimitBlockedException(String message) {
        super(message);
    }
}
package com.sha.rabbitdemo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ControllerAdvisor extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<Object> handleApiException(ApiException e) {
        HttpStatus badRequest = HttpStatus.INTERNAL_SERVER_ERROR;
        return new ResponseEntity<>(e.getMessage(), badRequest);
    }
}

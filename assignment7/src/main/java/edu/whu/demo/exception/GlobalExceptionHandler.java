package edu.whu.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;


//捕获controller（全局）异常
@ControllerAdvice
public class GlobalExceptionHandler {
//
    @ExceptionHandler(ProductAdminException.class)
    public ResponseEntity<ExceptionResponse> handleExceptions(ProductAdminException exception, WebRequest webRequest) {
        ExceptionResponse response = new ExceptionResponse();

        response.setMessage(exception.getMessage());
        ResponseEntity<ExceptionResponse> entity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        return entity;
    }

}

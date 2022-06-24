package com.tribal.creditlineapi.handleException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ResourceExceptionHandler {
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalError> handleMethodArgumentNotValidException(
        MethodArgumentNotValidException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getFieldError().getField()+"::"+e.getFieldError().getDefaultMessage(), e.getFieldError().getCode());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(globalError);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalError> handleHttpMessageNotReadableException(
        HttpMessageNotReadableException e, HttpServletRequest request
    ){
        GlobalError globalError = new GlobalError(e.getMessage(), e.getLocalizedMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(globalError);
    }

}

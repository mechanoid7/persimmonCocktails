package com.example.persimmoncocktails.configurations;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class ValidationHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {

        Map<String, Object> errors = new HashMap<>();
//        ex.getBindingResult().getAllErrors().forEach((error) ->{
//
//            String fieldName = ((FieldError) error).getField();
//            String message = error.getDefaultMessage();
//            errors.put(fieldName, message);
//        });
        errors.put("validation-error", true);
        errors.put("timestamp", new Date());
        errors.put("message", ex.getBindingResult().getAllErrors()
                .stream()
                .map((error) ->{
                    String fieldName = ((FieldError) error).getField();
                    String message = error.getDefaultMessage();
                    return new ErrorField(fieldName, message);
                }).collect(Collectors.toList()));
        return new ResponseEntity<Object>(errors, HttpStatus.BAD_REQUEST);
    }

    @Data
    @AllArgsConstructor
    private class ErrorField{
        String field;
        String error;
    }
}
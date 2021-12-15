package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NullException extends ResponseStatusException {
    public NullException(String entity) {
        super(HttpStatus.BAD_REQUEST, entity);
    }
}

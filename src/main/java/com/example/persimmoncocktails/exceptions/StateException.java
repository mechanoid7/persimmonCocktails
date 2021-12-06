package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class StateException extends ResponseStatusException {
    public StateException(String m) {
        super(HttpStatus.CONFLICT, m);
    }
}

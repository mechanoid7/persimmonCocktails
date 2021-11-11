package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class UnknownException extends ResponseStatusException {

    public UnknownException() {
        super(HttpStatus.BAD_REQUEST, "Unknown");
    }

    public UnknownException(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

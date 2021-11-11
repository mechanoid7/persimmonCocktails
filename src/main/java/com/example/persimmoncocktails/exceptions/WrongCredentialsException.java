package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class WrongCredentialsException extends ResponseStatusException {

    public WrongCredentialsException() {
        super(HttpStatus.UNAUTHORIZED, "Wrong credentials");
    }

    public WrongCredentialsException(String reason) {
        super(HttpStatus.UNAUTHORIZED, reason);
    }
}

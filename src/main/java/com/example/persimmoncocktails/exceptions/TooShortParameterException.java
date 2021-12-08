package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TooShortParameterException extends ResponseStatusException {
    public TooShortParameterException(String parameter, Integer requiredLength) {
        super(HttpStatus.CONFLICT, String.format("%s is too short, it must be at least %d chars long", parameter, requiredLength));
    }
}

package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectEmailFormat extends ResponseStatusException {

    public IncorrectEmailFormat() {
        super(HttpStatus.BAD_REQUEST, "Your email has incorrect format");
    }

    public IncorrectEmailFormat(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

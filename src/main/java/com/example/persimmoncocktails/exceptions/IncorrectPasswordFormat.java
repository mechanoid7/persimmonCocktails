package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectPasswordFormat extends ResponseStatusException {

    static final String PASSWORD_REQUIREMENTS = "Password should be at least 8 characters long";

    public IncorrectPasswordFormat() {
        super(HttpStatus.BAD_REQUEST, PASSWORD_REQUIREMENTS);
    }

    public IncorrectPasswordFormat(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

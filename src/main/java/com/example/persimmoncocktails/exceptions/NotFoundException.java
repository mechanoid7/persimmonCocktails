package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NotFoundException extends ResponseStatusException {
    public NotFoundException(String entity) {
        super(HttpStatus.BAD_REQUEST, "Such "+entity+" doesn't exist");
    }
}

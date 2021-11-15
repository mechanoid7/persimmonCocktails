package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateException extends ResponseStatusException {
    public DuplicateException(String entity) {
        super(HttpStatus.BAD_REQUEST, "Such "+entity+" already exists");
    }
}

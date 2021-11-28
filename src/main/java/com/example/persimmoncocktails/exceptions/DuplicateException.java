package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class DuplicateException extends ResponseStatusException {
    public DuplicateException(String entity) {
        super(HttpStatus.BAD_REQUEST, "Such "+entity+" already exists");
    }

    public DuplicateException(String entity, String field) {
        super(HttpStatus.BAD_REQUEST, entity+" with such "+field+" already exists");
    }
}

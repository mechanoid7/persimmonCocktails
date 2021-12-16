package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectImage extends ResponseStatusException {
    public IncorrectImage() {
        super(HttpStatus.BAD_REQUEST, "Incorrect image");
    }
}

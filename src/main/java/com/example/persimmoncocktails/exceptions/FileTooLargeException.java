package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class FileTooLargeException extends ResponseStatusException {
    public FileTooLargeException(String maxSize) {
        super(HttpStatus.LOCKED, "Uploaded file is too large. Max size: " + maxSize);
    }
}
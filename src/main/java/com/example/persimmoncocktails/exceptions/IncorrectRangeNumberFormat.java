package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectRangeNumberFormat extends ResponseStatusException {

    public IncorrectRangeNumberFormat() {
        super(HttpStatus.BAD_REQUEST, "The specified number is out of range.");
    }

    public IncorrectRangeNumberFormat(String of) {
        super(HttpStatus.BAD_REQUEST, "The specified number " + of + " is out of range.");
    }
}

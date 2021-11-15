package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class IncorrectNameFormat extends ResponseStatusException {


    public IncorrectNameFormat() {
        super(HttpStatus.BAD_REQUEST, "Provided name has incorrect format");
    }

    public IncorrectNameFormat(String reason) {
        super(HttpStatus.BAD_REQUEST, reason);
    }
}

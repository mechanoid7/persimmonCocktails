package com.example.persimmoncocktails.exceptions;

import com.fasterxml.classmate.GenericType;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class LinkExpired extends ResponseStatusException {
    public LinkExpired(String entity) {
        super(HttpStatus.NOT_ACCEPTABLE, entity + " link expired");
    }
}

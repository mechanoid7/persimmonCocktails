package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RoleException extends ResponseStatusException {
    public RoleException() {
        super(HttpStatus.CONFLICT, "It is forbidden to change the user's role");
    }
}

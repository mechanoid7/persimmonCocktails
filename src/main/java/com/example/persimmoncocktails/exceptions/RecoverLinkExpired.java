package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class RecoverLinkExpired extends ResponseStatusException {
    public RecoverLinkExpired() {
        super(HttpStatus.NOT_ACCEPTABLE, "Recover link expired");
    }
}

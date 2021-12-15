package com.example.persimmoncocktails.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidImageExtensionException extends ResponseStatusException {
    public InvalidImageExtensionException() {
        super(HttpStatus.LOCKED, "You cannot upload files of this type. Permitted formats: JPG, PNG, BMP, GIF, TIF, WEBP, HEIC");
    }
}
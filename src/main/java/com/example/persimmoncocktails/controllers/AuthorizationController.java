package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> authorizeUser(@RequestParam String username, @RequestParam String password) {
        return authorizationService.authorizeUser(username, password);
    }

    @PostMapping(path = "/l")
    public ResponseEntity<String> aut(@RequestParam String username, @RequestParam String password) {
        return authorizationService.authorizeUser(username, password);
    }

}

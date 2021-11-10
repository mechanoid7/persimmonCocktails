package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.impl.PersonDaoImpl;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity authorizeUser(@RequestParam String email, @RequestParam String password) {
        return authorizationService.authorizeUser(email, password);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<Long> registerUser(@RequestParam String name, @RequestParam String password, @RequestParam String email) {
        return ResponseEntity.ok(authorizationService.registerUser(name, email, password));
    }

    @PostMapping(path = "/logout")
    public ResponseEntity<String> logoutUser() {
        return authorizationService.logoutUser();
    }


    @PostMapping(path = "/recover")
    public ResponseEntity<String> recoverPassword(@RequestParam String email) {
        return authorizationService.recoverPassword(email);
    }
}



package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.impl.PersonDaoImpl;
import com.example.persimmoncocktails.dtos.ResponseMessage;
import com.example.persimmoncocktails.dtos.auth.RequestRegistrationDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestSigninDataDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@RestController
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(path = "/login")
    public void authorizeUser(@Valid @RequestBody RequestSigninDataDto signinData) {
        authorizationService.authorizeUser(signinData);
    }

    @PostMapping(path = "/registration")
    public ResponseEntity<Long> registerUser(@Valid @RequestBody RequestRegistrationDataDto registrationData) {
        return ResponseEntity.ok(authorizationService.registerUser(registrationData));
    }

    @PostMapping(path = "/logout")
    public void logoutUser() {
        authorizationService.logoutUser();
    }


    @PostMapping(path = "/recover")
    public ResponseEntity<ResponseMessage> recoverPassword(@RequestParam String email) {
        return ResponseEntity.ok(authorizationService.recoverPassword(email));
    }
}



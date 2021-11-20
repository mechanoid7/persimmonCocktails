package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.ResponseMessage;
import com.example.persimmoncocktails.dtos.auth.RequestRegistrationDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestSigninDataDto;
import com.example.persimmoncocktails.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(path = "/registration")
    public Long registerUser(@Valid @RequestBody RequestRegistrationDataDto registrationData) {
        return authorizationService.registerUser(registrationData);
    }

    @PostMapping(path = "/logout")
    public void logoutUser() {
        authorizationService.logoutUser();
    }


    @PostMapping(path = "/forgot-password")
    public void forgotPassword(@RequestParam String email) {
        authorizationService.forgotPassword(email);
    }

    @PostMapping(path = "/recover-password")
    public void recoverPassword(@RequestParam String id, @RequestParam Long personId, @RequestParam String newPassword){ // get id, personId from email link
        authorizationService.recoverPassword(id, personId, newPassword);
    }
}



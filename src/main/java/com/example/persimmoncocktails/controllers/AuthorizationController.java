package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.auth.RequestEmailDto;
import com.example.persimmoncocktails.dtos.auth.RequestRecoverPasswordDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestRegistrationDataDto;
import com.example.persimmoncocktails.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void registerUser(@Valid @RequestBody RequestRegistrationDataDto registrationData) {
        authorizationService.registerUser(registrationData);
    }


    @PostMapping(path = "/forgot-password")
    public void forgotPassword(@RequestBody RequestEmailDto emailData) {
        authorizationService.forgotPassword(emailData.getEmail());
    }

    @PostMapping(path = "/recover-password")
    public void recoverPassword(@RequestBody RequestRecoverPasswordDataDto requestRecoverPasswordData) { // get id, personId from email link
//        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        authorizationService.recoverPassword(
                requestRecoverPasswordData.getId(),
                requestRecoverPasswordData.getPersonId(),
                requestRecoverPasswordData.getNewPassword());
    }
}



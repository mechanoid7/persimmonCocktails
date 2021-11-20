package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.ResponseMessage;
import com.example.persimmoncocktails.dtos.auth.RequestEmailDto;
import com.example.persimmoncocktails.dtos.auth.RequestRecoverPasswordDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestRegistrationDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestSigninDataDto;
import com.example.persimmoncocktails.services.AuthorizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@CrossOrigin()
@RestController
public class AuthorizationController {
    private final AuthorizationService authorizationService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @PostMapping(path = "/login")
    public Long authorizeUser(@Valid @RequestBody RequestSigninDataDto signinData) {
        return authorizationService.authorizeUser(signinData);
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
    public void forgotPassword(@RequestBody RequestEmailDto emailData) {
        authorizationService.forgotPassword(emailData.getEmail());
    }

    @PostMapping(path = "/recover-password")
    public void recoverPassword(@RequestBody RequestRecoverPasswordDataDto requestRecoverPasswordData){ // get id, personId from email link
        authorizationService.recoverPassword(
                requestRecoverPasswordData.getId(),
                requestRecoverPasswordData.getPersonId(),
                requestRecoverPasswordData.getNewPassword());
    }
}



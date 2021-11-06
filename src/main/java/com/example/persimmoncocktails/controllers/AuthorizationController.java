package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.services.AuthorizationService;
import com.example.persimmoncocktails.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController
public class AuthorizationController {
    private final AuthorizationService authorizationService;
    private final MailService mailService;

    @Autowired
    public AuthorizationController(AuthorizationService authorizationService, MailService mailService) {
        this.authorizationService = authorizationService;
        this.mailService = mailService;
    }

    @PostMapping(path = "/login")
    public ResponseEntity<String> authorizeUser(@RequestParam String username, @RequestParam String password) {
        return authorizationService.authorizeUser(username, password);
    }

//    @PostMapping(path = "/l")
//    public ResponseEntity<String> aut(@RequestParam String username, @RequestParam String password) {
//        return authorizationService.authorizeUser(username, password);
//    }

    @PostMapping(path = "/registration")
    public ResponseEntity<String> registerUser(@RequestParam String username, @RequestParam String password, @RequestParam String email) {
        return authorizationService.registerUser(username, password, email);
    }

    @PostMapping(path ="/logout")
    public ResponseEntity<String> logoutUser() {
        return authorizationService.logoutUser();
    }

    @PostMapping(path="/test")
    public ResponseEntity<String> testMailSender() {
        try {
            mailService.sendEmail();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @PostMapping(path = "/recover")
    public ResponseEntity<String> recoverPassword(@RequestParam String email) {
        return authorizationService.recoverPassword(email);
    }
}



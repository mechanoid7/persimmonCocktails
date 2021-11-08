package com.example.persimmoncocktails.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class EmailController {

    @Autowired
    public JavaMailSender emailSender;

    @ResponseBody
    @RequestMapping("/sendEmail")
    public String sendEmail(@RequestParam String email) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("email verification code");
        message.setText("1111"); //generate code and save to DB

        // Send Message
        this.emailSender.send(message);

        return "Email Sent!";
    }

    public ResponseEntity<String> checkEmailVerificationCode(@RequestParam String code) {
        //compare to code from DB
        return ResponseEntity.ok("successful verification"); //return userDto
    }

}
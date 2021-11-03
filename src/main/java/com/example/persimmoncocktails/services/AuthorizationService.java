package com.example.persimmoncocktails.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService {
    public ResponseEntity authorizeUser(String username, String password){  //change on user
        return ResponseEntity.ok("user authorized");
    }
}

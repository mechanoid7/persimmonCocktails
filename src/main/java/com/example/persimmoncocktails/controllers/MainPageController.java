package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.impl.PersonDaoImpl;
import com.example.persimmoncocktails.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
public class MainPageController {

    @GetMapping("/")
    String root(){
        return "Hello, i`m Persimmon!";
    }

}

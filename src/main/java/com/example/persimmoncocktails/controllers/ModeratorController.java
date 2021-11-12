package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.impl.ModeratorDaoImpl;
import com.example.persimmoncocktails.models.Person;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    private final ModeratorDaoImpl moderatorDaoImpl;

    public ModeratorController(ModeratorDaoImpl moderatorDaoImpl) {
        this.moderatorDaoImpl = moderatorDaoImpl;
    }

    @GetMapping("/get-all")
    public List<Person> getAllModerators(){
        return moderatorDaoImpl.getAllModerators();
    }
}

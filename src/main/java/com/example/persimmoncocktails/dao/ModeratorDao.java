package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.models.Person;

import java.util.List;

public interface ModeratorDao {
    List<Person> getAllModerators();
}

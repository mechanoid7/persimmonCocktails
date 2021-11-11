package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.models.Person;

public interface PersonDao {
    void create(Person person);

    Person read(Long personId);

    Person readByEmail(String email);

    void update(Person person);

    void delete(Long personId);
}
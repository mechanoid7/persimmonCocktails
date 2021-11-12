package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.models.Person;

import java.util.List;

public interface PersonDao {
    void create(Person person);

    Person read(Long personId);

    Person read(String email);

    void update(Person person);

    void delete(Long personId);

    void changePassword(Long personId, String oldPassword, String newPassword);

    List<Person> getPersonFriends(Long personId);

    List<Person> getListFriendBySubstring(Long personId, String substring);
}
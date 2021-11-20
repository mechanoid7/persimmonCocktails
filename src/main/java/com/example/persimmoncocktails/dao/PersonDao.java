package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.auth.RestorePasswordDataDto;
import com.example.persimmoncocktails.models.Person;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonDao {
    boolean existsById(Long personId);

    void create(Person person);

    Person read(Long personId);

    Person readByEmail(String email);

    void update(Person person);

    void delete(Long personId);

    void changePassword();

    void saveRecoverPasswordRequest(Long personId, LocalDateTime localDateTime, String hashedId);

    List<RestorePasswordDataDto> restorePassword(Long personId);

    void deactivateRequestsBuPersonId(Long personId);
}
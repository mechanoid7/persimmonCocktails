package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.models.Person;
import org.apache.tomcat.jni.Time;

import java.time.LocalDateTime;
import java.util.List;

public interface PersonDao {
    boolean existsById(Long personId);

    void create(Person person);

    Person read(Long personId);

    Person readByEmail(String email);

    void update(Person person);

    void delete(Long personId);

    void changePassword(Long personId, String newPassword);

    List<Person> getPersonFriends(Long personId);

    List<Person> getListFriendBySubstring(Long personId, String substring);

    void saveRecoverPasswordRequest(Long personId, LocalDateTime localDateTime, String hashedId);

    void restorePassword(String id, Long personId,String newPassword);
}
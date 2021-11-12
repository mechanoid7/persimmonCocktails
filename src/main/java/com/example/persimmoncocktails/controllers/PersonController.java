package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.impl.PersonDaoImpl;
import com.example.persimmoncocktails.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonDaoImpl personDaoImpl;

    public PersonController(PersonDaoImpl personDaoImpl) {
        this.personDaoImpl = personDaoImpl;
    }

    @PostMapping("/add")
    private HttpStatus addPerson(@RequestParam String name, @RequestParam String  email, @RequestParam String password,
                           @RequestParam Long photoId, @RequestParam Long blogId, @RequestParam Integer roleId){
        personDaoImpl.create(new Person(0L, name, email, password, photoId, blogId, roleId));
        return HttpStatus.CREATED;
    }

    @GetMapping("/{personId}")
    public Person getPersonById(@PathVariable Long personId){
        return personDaoImpl.read(personId);
    }

    @GetMapping("/email={personEmail}")
    private Person getPersonByEmail(@PathVariable String personEmail){
        return personDaoImpl.read(personEmail);
    }

    @PatchMapping("/update")
    private HttpStatus updatePerson(@RequestParam Long personId, @RequestParam String name, @RequestParam String  email,
                              @RequestParam String password, @RequestParam Long photoId, @RequestParam Long blogId,
                              @RequestParam Integer roleId){
        personDaoImpl.update(new Person(personId, name, email, password, photoId, blogId, roleId));
        return HttpStatus.OK;
    }

    @DeleteMapping("/{personId}")
    private HttpStatus deletePersonById(@PathVariable Long personId){
        personDaoImpl.delete(personId);
        return HttpStatus.ACCEPTED;
    }

    @PatchMapping("/change-password")
    private HttpStatus changePasswordPerson(@RequestParam Long personId, @RequestParam String oldPassword,
                                      @RequestParam String  newPassword){
        personDaoImpl.changePassword(personId, oldPassword, newPassword);
        return HttpStatus.OK;
    }

    @GetMapping("/{personId}/friends")
    private List<Person> getPersonFriends(@PathVariable Long personId){
        return personDaoImpl.getPersonFriends(personId);
    }

    @GetMapping("/{personId}/friends/{substring}")
    private List<Person> getPersonFriendsBySubstring(@PathVariable Long personId, @PathVariable String substring){
        return personDaoImpl.getListFriendBySubstring(personId, "%"+substring+"%");
    }
}

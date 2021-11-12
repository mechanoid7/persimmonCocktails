package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonDao personDao;

    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

    @PostMapping("/add")
    private void addPerson(@RequestParam String name, @RequestParam String  email, @RequestParam String password
            , @RequestParam Integer roleId){
        personDao.create(
                Person.builder()
                        .name(name)
                        .email(email)
                        .password(password)
                        .roleId(roleId)
                        .build());
    }

    @GetMapping("/{personId}")
    public Person getPersonById(@PathVariable Long personId){
        return personDao.read(personId);
    }

    @GetMapping("/email={personEmail}")
    private Person getPersonByEmail(@PathVariable String personEmail){
        return personDao.read(personEmail);
    }

    @PatchMapping("/update")
    private HttpStatus updatePerson(@RequestParam Long personId, @RequestParam String name, @RequestParam String  email,
                              @RequestParam String password, @RequestParam Long photoId, @RequestParam Long blogId,
                              @RequestParam Integer roleId){
        personDao.update(new Person(personId, name, email, password, photoId, blogId, roleId));
        return HttpStatus.OK;
    }

    @DeleteMapping("/{personId}")
    private HttpStatus deletePersonById(@PathVariable Long personId){
        personDao.delete(personId);
        return HttpStatus.ACCEPTED;
    }

    @PatchMapping("/change-password")
    private HttpStatus changePasswordPerson(@RequestParam Long personId, @RequestParam String oldPassword,
                                      @RequestParam String  newPassword){
        personDao.changePassword(personId, oldPassword, newPassword);
        return HttpStatus.OK;
    }

    @GetMapping("/{personId}/friends")
    private List<Person> getPersonFriends(@PathVariable Long personId){
        return personDao.getPersonFriends(personId);
    }

    @GetMapping("/{personId}/friends/{substring}")
    private List<Person> getPersonFriendsBySubstring(@PathVariable Long personId, @PathVariable String substring){
        return personDao.getListFriendBySubstring(personId, "%"+substring+"%");
    }
}

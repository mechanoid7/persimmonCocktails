package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dao.impl.PersonDaoImpl;
import com.example.persimmoncocktails.models.Person;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonDao personDao;

    public PersonController(PersonDao personDao) {
        this.personDao = personDao;
    }

//    @PostMapping("/add")
//    private void addPerson(@RequestParam String name, @RequestParam String  email, @RequestParam String password
//            , @RequestParam Integer roleId){
//        personDaoImpl.create(
//                Person.builder()
//                        .name(name)
//                        .email(email)
//                        .password(password)
//                        .roleId(roleId)
//                .build());
//    }

    @GetMapping("/{personId}")
    public Person getPersonById(@PathVariable Long personId){
        return personDao.read(personId);
    }

    @GetMapping("/email/{personEmail}")
    private Person getPersonByEmail(@PathVariable String personEmail){
        return personDao.readByEmail(personEmail);
    }

//    @PatchMapping("/update")
//    private void updatePerson(@RequestParam Long personId, @RequestParam String name, @RequestParam String  email,
//                              @RequestParam String password, @RequestParam Long photoId, @RequestParam Long blogId,
//                              @RequestParam Integer roleId){
//        personDaoImpl.update(new Person(personId, name, email, password, photoId, blogId, roleId));
//    }

    @DeleteMapping("/{personId}")
    private void deletePersonById(@PathVariable Long personId){
        personDao.delete(personId);
    }
}

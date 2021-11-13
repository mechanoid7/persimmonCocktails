package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.PersonDao;

import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }


//    @PostMapping("/add")
//    private void addPerson(@RequestParam String name, @RequestParam String  email, @RequestParam String password
//            , @RequestParam Integer roleId){
//        personDao.create(
//                Person.builder()
//                        .name(name)
//                        .email(email)
//                        .password(password)
//                        .roleId(roleId)
//                        .build());
//    }

    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId){
        return personService.readPersonById(personId);
    }

    @GetMapping("/email/{personEmail}")
    private PersonResponseDto getPersonByEmail(@PathVariable String personEmail){
        return personService.readByEmail(personEmail);
    }

    @PatchMapping("/update-name")
    private void updateName(@RequestParam Long personId, @RequestParam String name){
        personService.updateName(personId, name);
    }

    @PatchMapping("/update-photo")
    private void updatePhoto(@RequestParam Long personId, @RequestParam Long photoId){
        personService.updatePhotoId(personId, photoId);
    }

    @DeleteMapping("/{personId}")
    private void deletePersonById(@PathVariable Long personId){
        personService.delete(personId);
    }

    @PatchMapping("/change-password")
    private void changePasswordPerson(@RequestParam Long personId, @RequestParam String oldPassword,
                                      @RequestParam String  newPassword){
        personService.changePassword(personId, oldPassword, newPassword);
    }

    @GetMapping("/{personId}/friends")
    private List<PersonResponseDto> getPersonFriends(@PathVariable Long personId){
        return personService.getPersonFriends(personId);
    }

    @GetMapping("/{personId}/friends/{substring}")
    private List<PersonResponseDto> getPersonFriendsBySubstring(@PathVariable Long personId, @PathVariable String substring){
        return personService.getListFriendBySubstring(personId, substring);
    }
}

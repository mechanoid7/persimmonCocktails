package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.PersonDao;

import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
@PreAuthorize("isAuthenticated()")
public class PersonController {

    private final PersonService personService;

    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }



    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId){
        return personService.readPersonById(personId);
    }

    @GetMapping("/email/{personEmail}")
    public PersonResponseDto getPersonByEmail(@PathVariable String personEmail){
        return personService.readByEmail(personEmail);
    }

    @PatchMapping("/update-name")
    public void updateName(@RequestParam Long personId, @RequestParam String name){
        personService.updateName(personId, name);
    }

    @PatchMapping("/update-photo")
    public void updatePhoto(@RequestParam Long personId, @RequestParam Long photoId){
        personService.updatePhotoId(personId, photoId);
    }

    @DeleteMapping("/{personId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePersonById(@PathVariable Long personId){
        personService.delete(personId);
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public void changePasswordPerson(@RequestParam Long personId, @RequestParam String oldPassword,
                                      @RequestParam String  newPassword){
        personService.changePassword(personId, oldPassword, newPassword);
    }

    @GetMapping("/{personId}/friends")
    public List<PersonResponseDto> getPersonFriends(@PathVariable Long personId){
        return personService.getPersonFriends(personId);
    }

    @GetMapping("/{personId}/friends/{substring}")
    public List<PersonResponseDto> getPersonFriendsBySubstring(@PathVariable Long personId, @PathVariable String substring){
        return personService.getListFriendBySubstring(personId, substring);
    }
}

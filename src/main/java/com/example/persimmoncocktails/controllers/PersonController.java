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
import org.springframework.security.core.context.SecurityContextHolder;
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
    public void updateName(@RequestParam String name){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        personService.updateName(personId, name);
    }

    @PatchMapping("/update-photo")
    public void updatePhoto(@RequestParam Long photoId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        personService.updatePhotoId(personId, photoId);
    }

    @DeleteMapping()
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePersonById(@PathVariable Long personId){
        personService.delete(personId);
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasRole('ROLE_CLIENT')")
    public void changePasswordPerson(@RequestParam String oldPassword,
                                      @RequestParam String  newPassword){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        personService.changePassword(personId, oldPassword, newPassword);
    }

    @GetMapping("/friends")
    public List<PersonResponseDto> getPersonFriends(){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return personService.getPersonFriends(personId);
    }

    @GetMapping("/friends/{substring}")
    public List<PersonResponseDto> getPersonFriendsBySubstring(@PathVariable String substring){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return personService.getListFriendBySubstring(personId, substring);
    }
}

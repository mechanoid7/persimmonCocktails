package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.PersonDao;

import com.example.persimmoncocktails.dtos.auth.RequestChangePasswordDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestUpdateNameDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestUpdatePhotoDataDto;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.PersonService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin()
@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;

    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId){
        return personService.readPersonById(personId);
    }

    @GetMapping("/email/{personEmail}")
    private PersonResponseDto getPersonByEmail(@PathVariable String personEmail){
        return personService.readByEmail(personEmail);
    }

    @PatchMapping("/update-name")
    private void updateName(@RequestBody RequestUpdateNameDataDto requestUpdateNameData){
        personService.updateName(requestUpdateNameData.getPersonId(), requestUpdateNameData.getName());
    }
//
    @PatchMapping("/update-photo")
    private void updatePhoto(@RequestBody RequestUpdatePhotoDataDto requestUpdatePhotoData){
        personService.updatePhotoId(requestUpdatePhotoData.getPersonId(), requestUpdatePhotoData.getPhotoId());
    }

    @DeleteMapping("/{personId}")
    private void deletePersonById(@PathVariable Long personId){
        personService.delete(personId);
    }

    @PatchMapping("/change-password")
    private void changePasswordPerson(@RequestBody RequestChangePasswordDataDto requestChangePasswordData){
        personService.changePassword(
                requestChangePasswordData.getPersonId(),
                requestChangePasswordData.getOldPassword(),
                requestChangePasswordData.getNewPassword());
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


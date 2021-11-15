package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.ModeratorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    private final ModeratorService moderatorService;
    private final PersonDao personDao;

    public ModeratorController(ModeratorService moderatorService, PersonDao personDao) {
        this.moderatorService = moderatorService;
        this.personDao = personDao;
    }


    @GetMapping("/all")
    public List<PersonResponseDto> getAllModerators() {
        return moderatorService.getAllModerators();
    }

    @PostMapping("/add")
    private void addModerator(@RequestParam String name, @RequestParam String email) {
        moderatorService.create(name, email);
    }

    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId){
        return moderatorService.readModeratorById(personId);
    }

    @PatchMapping("/update-name")
    private void updateName(@RequestParam Long personId, @RequestParam String name){
        moderatorService.updateName(personId, name);
    }

    @PatchMapping("/update-photo")
    private void updatePhoto(@RequestParam Long personId, @RequestParam Long photoId){
        moderatorService.updatePhotoId(personId, photoId);
    }

    @PatchMapping("/change-password")
    private void changePasswordPerson(@RequestParam Long personId, @RequestParam String oldPassword,
                                      @RequestParam String  newPassword){
        moderatorService.changePassword(personId, oldPassword, newPassword);
    }

    @PostMapping(path = "/create-password")
    public void recoverPassword(@RequestParam String id, @RequestParam Long personId, @RequestParam String newPassword){ // get id, personId from email link
        moderatorService.createPassword(id, personId, newPassword);
    }
}

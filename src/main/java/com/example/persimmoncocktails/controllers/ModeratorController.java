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
    private void addModerator(@RequestBody String name, @RequestBody String email) {
        moderatorService.create(name, email);
    }

    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId){
        return moderatorService.readModeratorById(personId);
    }

    @PatchMapping("/update-name")
    private void updateName(@RequestParam Long personId, @RequestBody String name){
        moderatorService.updateName(personId, name);
    }

    @PatchMapping("/update-photo")
    private void updatePhoto(@RequestParam Long personId, @RequestBody Long photoId){
        moderatorService.updatePhotoId(personId, photoId);
    }

    @PatchMapping("/change-password")
    private void changePasswordPerson(@RequestBody Long personId, @RequestBody String oldPassword,
                                      @RequestBody String  newPassword){
        moderatorService.changePassword(personId, oldPassword, newPassword);
    }

    @PostMapping(path = "/create-password")
    public void recoverPassword(@RequestBody String id, @RequestBody Long personId, @RequestBody String newPassword){ // get id, personId from email link
        moderatorService.createPassword(id, personId, newPassword);
    }
}

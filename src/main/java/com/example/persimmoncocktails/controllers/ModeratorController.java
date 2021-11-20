package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.auth.*;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.ModeratorService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin()
@RestController
@RequestMapping("/moderator")
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping("/all")
    public List<PersonResponseDto> getAllModerators() {
        return moderatorService.getAllModerators();
    }

    @PostMapping("/add")
    private void addModerator(@RequestBody RequestAddModeratorDataDto requestAddModeratorData) {
        moderatorService.create(requestAddModeratorData.getName(), requestAddModeratorData.getEmail());
    }

    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId){
        return moderatorService.readModeratorById(personId);
    }

    @PatchMapping("/update-name")
    private void updateName(@RequestBody RequestUpdateNameDataDto requestUpdateNameData){
        moderatorService.updateName(requestUpdateNameData.getPersonId(), requestUpdateNameData.getName());
    }

    @PatchMapping("/update-photo")
    private void updatePhoto(@RequestParam RequestUpdatePhotoDataDto requestUpdatePhotoData){
        moderatorService.updatePhotoId(requestUpdatePhotoData.getPersonId(), requestUpdatePhotoData.getPhotoId());
    }

    @PatchMapping("/change-password")
    private void changePasswordPerson(@RequestBody RequestChangePasswordDataDto requestChangePasswordData){
        moderatorService.changePassword(
                requestChangePasswordData.getPersonId(),
                requestChangePasswordData.getOldPassword(),
                requestChangePasswordData.getNewPassword());
    }

    @PostMapping(path = "/create-password")
    public void recoverPassword(@RequestBody RequestCreatePasswordDataDto requestCreatePasswordData){ // get id, personId from email link
        moderatorService.createPassword(
                requestCreatePasswordData.getId(),
                requestCreatePasswordData.getPersonId(),
                requestCreatePasswordData.getNewPassword());
    }
}

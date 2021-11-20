package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Person;
import com.example.persimmoncocktails.services.ModeratorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/moderator")
@PreAuthorize("isAuthenticated()")
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }


    @GetMapping("/all")
    @PreAuthorize("hasAuthority('moderator:read')")
    public List<PersonResponseDto> getAllModerators() {
        return moderatorService.getAllModerators();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addModerator(@RequestParam String name, @RequestParam String email) {
//        personDao.create(
//                Person.builder()
//                        .name(name)
//                        .email(email)
//                        .password(password)
//                        .roleId(roleId)
//                        .build());
    }

    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId){
        return moderatorService.readModeratorById(personId);
    }

    @PatchMapping("/update-name")
    @PreAuthorize("hasPermission('moderator:update')")
    public void updateName(@RequestParam String name){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        moderatorService.updateName(personId, name);
    }

    @PatchMapping("/update-photo")
    @PreAuthorize("hasPermission('moderator:update')")
    public void updatePhoto(@RequestParam Long photoId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        moderatorService.updatePhotoId(personId, photoId);
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasPermission('moderator:update')")
    public void changePasswordPerson(@RequestParam String oldPassword,
                                      @RequestParam String  newPassword){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        moderatorService.changePassword(personId, oldPassword, newPassword);
    }

}

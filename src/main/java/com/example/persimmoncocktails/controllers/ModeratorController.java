package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.auth.*;
import com.example.persimmoncocktails.dtos.person.*;
import com.example.persimmoncocktails.services.ModeratorService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
@RequestMapping("/moderator")
public class ModeratorController {

    private final ModeratorService moderatorService;

    public ModeratorController(ModeratorService moderatorService) {
        this.moderatorService = moderatorService;
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('moderator:read')")
    public List<ModeratorResponseDto> getAllModerators() {
        return moderatorService.getAllModerators();
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void addModerator(@RequestBody RequestAddModeratorDataDto requestAddModeratorData) {
        moderatorService.create(requestAddModeratorData.getName(), requestAddModeratorData.getEmail());
    }

    @GetMapping("/{personId}")
    public ModeratorResponseDto getModeratorById(@PathVariable Long personId) {
        return moderatorService.readModeratorById(personId);
    }

    @PatchMapping("/update-name")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updateName(@RequestBody UpdateNameForModeratorRequestDto dto) {
        moderatorService.updateName(dto.getPersonId(), dto.getName());
    }

    @PatchMapping("/update-photo")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void updatePhoto(@RequestBody UpdatePhotoForModeratorRequestDto dto) {
        moderatorService.updatePhotoId(dto.getPersonId(), dto.getPhotoId());
    }

    @PatchMapping("/change-password")
    @PreAuthorize("hasPermission('moderator:update')")
    public void changePasswordPerson(@RequestBody RequestChangePasswordDataDto requestChangePasswordDto) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        moderatorService.changePassword(personId, requestChangePasswordDto.getOldPassword(), requestChangePasswordDto.getOldPassword());
    }

    @PostMapping(path = "/create-password")
    public void recoverPassword(@RequestBody RequestCreatePasswordDataDto requestCreatePasswordData) { // get id, personId from email link
        moderatorService.createPassword(
                requestCreatePasswordData.getId(),
                requestCreatePasswordData.getPersonId(),
                requestCreatePasswordData.getNewPassword());
    }

    @PostMapping(path = "/change-status")
    @PreAuthorize("hasRole('ROLE_ADMIN')") // admin can change
    public void changeStatus(@RequestBody Long personId) {
        moderatorService.changeStatus(personId);
    }
}

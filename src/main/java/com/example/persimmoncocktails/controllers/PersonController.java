package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.auth.RequestChangePasswordDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestUpdateNameDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestUpdatePhotoDataDto;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.services.FriendsService;
import com.example.persimmoncocktails.services.FriendshipInvitationService;
import com.example.persimmoncocktails.services.PersonService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final FriendsService friendsService;


    public PersonController(PersonService personService, FriendsService friendsService) {
        this.personService = personService;
        this.friendsService = friendsService;
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
    private List<FriendResponseDto> getPersonFriendsById(@PathVariable Long personId, @RequestParam("page") Long pageNumber){
        return friendsService.getPersonFriends(personId, pageNumber);
    }

    @GetMapping("/{personId}/friends/{substring}")
    private List<FriendResponseDto> getPersonFriendsByIdAndSubstring(@PathVariable Long personId, @PathVariable String substring, @RequestParam("page") Long pageNumber){
        return friendsService.getListFriendsBySubstring(personId, substring, pageNumber);
    }

    @GetMapping("/users/{substring}")
    private List<FriendResponseDto> getPersonsByNameSubstring(@PathVariable String substring, @RequestParam("page") Long pageNumber){
        return friendsService.searchPersonsByNameSubstring(substring, pageNumber);
    }

    @DeleteMapping("/{personId}/friends/delete")
    private void deleteFriend(@PathVariable Long personId, @RequestBody Long friendId){
        friendsService.removeFriendById(personId, friendId);
    }

    @PostMapping("/{personId}/friends/add")
    private void addFriend(@PathVariable Long personId, @RequestBody Long friendId){
        friendsService.addFriend(personId, friendId);
    }
}


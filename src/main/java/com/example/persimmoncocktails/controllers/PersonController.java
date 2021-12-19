package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.auth.RequestChangePasswordDataDto;
import com.example.persimmoncocktails.dtos.friend.FoundPersonsResponseDto;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.friend.RequestDeleteFriendDto;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.services.FriendsService;
import com.example.persimmoncocktails.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@PreAuthorize("isAuthenticated")
@RequestMapping("/person")
public class PersonController {

    private final PersonService personService;
    private final FriendsService friendsService;

    @Autowired
    public PersonController(PersonService personService, FriendsService friendsService) {
        this.personService = personService;
        this.friendsService = friendsService;
    }


    @GetMapping("/{personId}")
    public PersonResponseDto getPersonById(@PathVariable Long personId) {
        return personService.readPersonById(personId);
    }

    @GetMapping("/email/{personEmail}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public PersonResponseDto getPersonByEmail(@PathVariable String personEmail) {
        return personService.readByEmail(personEmail);
    }

    @PatchMapping("/update-name")
    public void updateName(@RequestParam String name) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        personService.updateName(personId, name);
    }

    @PatchMapping("/update-photo")
    public void updatePhoto(@RequestParam Long photoId) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        personService.updatePhotoId(personId, photoId);
    }

    @DeleteMapping("/{personId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deletePersonById(@PathVariable Long personId) {
        personService.delete(personId);
    }

    @PatchMapping("/change-password")
    public void changePasswordPerson(@RequestBody RequestChangePasswordDataDto requestChangePasswordData) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        personService.changePassword(personId,
                requestChangePasswordData.getOldPassword(),
                requestChangePasswordData.getNewPassword());
    }

    @GetMapping("/search-all/{substring}")
    public List<FriendResponseDto> getPersonsBySubstring(@PathVariable String substring, @RequestParam("page") Long pageNumber) {
        return friendsService.searchPersonsByNameSubstring(substring, pageNumber);
    }

    @GetMapping("/search/{substring}")
    public List<FoundPersonsResponseDto> getPersonsBySubstringWithoutFriends(@PathVariable String substring, @RequestParam("page") Long pageNumber) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendsService.searchPersonsByNameSubstringWithoutFriends(personId, substring, pageNumber);
    }

    @GetMapping("/search-exists/{substring}")
    public Boolean isPersonsBySubstringWithoutFriendsExists(@PathVariable String substring, @RequestParam("page") Long pageNumber) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendsService.isPersonsBySubstringWithoutFriendsExists(personId, substring, pageNumber);
    }

    @GetMapping("/search-pages-number/{substring}")
    public Long numberOfPagesPersonsBySubstringWithoutFriends(@PathVariable String substring) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendsService.numberOfPagesPersonsBySubstringWithoutFriends(personId, substring);
    }

    @GetMapping("/friends")
    public List<FriendResponseDto> getSelfFriends(@RequestParam("page") Long pageNumber) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendsService.getPersonFriends(personId, pageNumber);
    }

    @GetMapping("/friends/{substring}") // use empty substring for get list of friends without search
    public List<FriendResponseDto> getPersonFriendsByIdAndSubstring(@PathVariable String substring, @RequestParam("page") Long pageNumber) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendsService.getListFriendsBySubstring(personId, substring, pageNumber);
    }

    @GetMapping("/friends-pages-number")
    public Long numberOfPagesFriends() {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendsService.numberOfPagesFriendsBySubstring(personId, "");
    }

    @GetMapping("/friends-pages-number/{substring}")
    public Long numberOfPagesFriendsBySubstring(@PathVariable String substring) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendsService.numberOfPagesFriendsBySubstring(personId, substring);
    }

    @DeleteMapping("/friends/delete")
    public void deleteFriend(@RequestBody RequestDeleteFriendDto deleteFriend) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        friendsService.removeFriendById(personId, deleteFriend.getPersonId());
    }

    @PostMapping("/friends/add")
    public void addFriend(@RequestBody Long friendId) {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        friendsService.addFriend(personId, friendId);
    }
}

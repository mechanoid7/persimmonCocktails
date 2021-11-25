package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.auth.RequestChangePasswordDataDto;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.models.Cocktail;
import com.example.persimmoncocktails.services.CocktailService;
import com.example.persimmoncocktails.services.FriendsService;
import com.example.persimmoncocktails.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cocktail")
public class CocktailController {

    private final CocktailService cocktailService;

    @Autowired
    public CocktailController(CocktailService cocktailService) {
        this.cocktailService = cocktailService;
    }


    @GetMapping("/{dishId}")
    public Cocktail getCocktailById(@PathVariable Long dishId) {
        return cocktailService.readById(dishId);
    }

//    @GetMapping("/email/{personEmail}")
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
//    public PersonResponseDto getPersonByEmail(@PathVariable String personEmail){
//        return personService.readByEmail(personEmail);
//    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PatchMapping("/update")
    public void updateName(@RequestBody Cocktail cocktail) {
        cocktailService.update(cocktail);
    }

    @PreAuthorize("hasRole('ROLE_MODERATOR')")
    @PatchMapping("/change-status")
    public void changeStatus(@RequestBody Long dishId) { // activate/deactivate
        cocktailService.changeStatus(dishId);
    }

    @PatchMapping("/is-active")
    public Boolean dishIsActive(@RequestBody Long dishId) {
        return cocktailService.isActive(dishId);
    }

    @PatchMapping("/like")
    public void addLike(@RequestBody Long dishId) {
        cocktailService.addLike(dishId);
    }

//
//    @PatchMapping("/change-password")
//    @PreAuthorize("hasRole('ROLE_CLIENT')")
//    public void changePasswordPerson(@RequestBody RequestChangePasswordDataDto requestChangePasswordData){
//        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
//        personService.changePassword(personId,
//                requestChangePasswordData.getOldPassword(),
//                requestChangePasswordData.getNewPassword());
//    }
//
//    @GetMapping("/search/{substring}")
//    public List<FriendResponseDto> getPersonsBySubstring(@PathVariable String substring, @RequestParam("page") Long pageNumber){
//        return friendsService.searchPersonsByNameSubstring(substring, pageNumber);
//    }
//
//    @GetMapping("/friends")
//    private List<FriendResponseDto> getPersonFriendsById(@RequestParam("page") Long pageNumber){
//        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
//        return friendsService.getPersonFriends(personId, pageNumber);
//    }
//
//    @GetMapping("/friends/{substring}")
//    public List<FriendResponseDto> getPersonFriendsByIdAndSubstring(@PathVariable String substring, @RequestParam("page") Long pageNumber){
//        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
//        return friendsService.getListFriendsBySubstring(personId, substring, pageNumber);
//    }
//
//    @DeleteMapping("/friends/delete")
//    private void deleteFriend(@RequestBody Long friendId){
//        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
//        friendsService.removeFriendById(personId, friendId);
//    }
//
//    @PostMapping("/friends/add")
//    private void addFriend(@RequestBody Long friendId){
//        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
//        friendsService.addFriend(personId, friendId);
//    }
}


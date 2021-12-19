package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import com.example.persimmoncocktails.dtos.friendshipInvitation.RequestAddFriendshipInvitationDto;
import com.example.persimmoncocktails.dtos.friendshipInvitation.RequestDeclineFriendshipInvitationDto;
import com.example.persimmoncocktails.services.FriendshipInvitationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class FriendshipInvitationController {

    private final FriendshipInvitationService friendshipInvitationService;

    public FriendshipInvitationController(FriendshipInvitationService friendshipInvitationService) {
        this.friendshipInvitationService = friendshipInvitationService;
    }

    @PostMapping("/friend-invitation/add")
    public void addFriendshipInvitation(@RequestBody RequestAddFriendshipInvitationDto requestAddFriendshipInvitationDto){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        friendshipInvitationService.addFriendshipInvitation(personId, requestAddFriendshipInvitationDto.getPersonReceiverId(), requestAddFriendshipInvitationDto.getMessage());
    }

    @DeleteMapping("/friendship-invitation/delete")
    public void deleteFriendshipInvitation(@RequestBody RequestDeclineFriendshipInvitationDto requestDeclineFriendshipInvitation){
        System.out.println("FRIEND ID:"+requestDeclineFriendshipInvitation.getPersonId());
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        friendshipInvitationService.deleteFriendshipInvitationById(personId, requestDeclineFriendshipInvitation.getPersonId());
    }

    @GetMapping("/friendship-invitations")
    public List<FriendshipInvitationResponseDto>  getPersonFriendshipInvitations(@RequestParam("page") Long pageNumber){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendshipInvitationService.getPersonFriendshipInvitations(personId, pageNumber);
    }

    @GetMapping("/friendship-invitations-amount-pages")
    public Long numberOfPagesFriends() {
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendshipInvitationService.getPagesAmountFriendshipInvitations(personId);
    }
}

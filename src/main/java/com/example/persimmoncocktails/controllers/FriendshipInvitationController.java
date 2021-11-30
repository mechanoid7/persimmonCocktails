package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import com.example.persimmoncocktails.dtos.friendshipInvitation.RequestFriendshipInvitationDto;
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
    public void addFriendshipInvitation(@RequestBody RequestFriendshipInvitationDto requestFriendshipInvitationDto){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        friendshipInvitationService.addFriendshipInvitation(personId, requestFriendshipInvitationDto.getPersonReceiverId(), requestFriendshipInvitationDto.getMassage());
    }

    @DeleteMapping("/friendship-invitation/delete")
    public void deleteFriendshipInvitation(@RequestBody RequestFriendshipInvitationDto  requestFriendshipInvitationDto){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        friendshipInvitationService.deleteFriendshipInvitationById(personId, requestFriendshipInvitationDto.getPersonReceiverId());
    }

    @GetMapping("/friendship-invitations")
    public List<FriendshipInvitationResponseDto>  getPersonFriendshipInvitations(@RequestParam("page") Long pageNumber){ /// @RequestBody Long pageNumber
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return friendshipInvitationService.getPersonFriendshipInvitations(personId, pageNumber);
    }
}

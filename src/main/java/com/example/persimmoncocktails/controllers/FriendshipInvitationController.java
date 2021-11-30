package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import com.example.persimmoncocktails.dtos.friendshipInvitation.RequestFriendshipInvitationDto;
import com.example.persimmoncocktails.services.FriendshipInvitationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/person")
public class FriendshipInvitationController {

    private final FriendshipInvitationService friendshipInvitationService;

    public FriendshipInvitationController(FriendshipInvitationService friendshipInvitationService) {
        this.friendshipInvitationService = friendshipInvitationService;
    }

    @PostMapping("/{personId}/friend-invitation/add")
    public void addFriendshipInvitation(@PathVariable Long personId, @RequestBody RequestFriendshipInvitationDto requestFriendshipInvitationDto){
        friendshipInvitationService.addFriendshipInvitation(personId, requestFriendshipInvitationDto.getPersonReceiverId(), requestFriendshipInvitationDto.getMassage());
    }

    @DeleteMapping("/{personId}/friendship-invitation/delete")
    public void deleteFriendshipInvitation(@PathVariable Long personId, @RequestBody RequestFriendshipInvitationDto  requestFriendshipInvitationDto){
        friendshipInvitationService.deleteFriendshipInvitationById(personId, requestFriendshipInvitationDto.getPersonReceiverId());
    }

    @GetMapping("/{personId}/friendship-invitations")
    public List<FriendshipInvitationResponseDto>  getPersonFriendshipInvitationsById(@PathVariable Long personId, @RequestParam("page") Long pageNumber){ /// @RequestBody Long pageNumber
        return friendshipInvitationService.getPersonFriendshipInvitations(personId, pageNumber);
    }
}

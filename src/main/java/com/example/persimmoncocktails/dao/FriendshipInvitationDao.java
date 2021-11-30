package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;

import java.util.List;

public interface FriendshipInvitationDao {

    void addFriendshipInvitation(Long personIdInitiator, Long personIdReceiver, String massage);

    void deleteFriendshipInvitation(Long personIdInitiator, Long personId);

    List<FriendshipInvitationResponseDto> getPersonFriendshipInvitations(Long personId, Long pageNumber);

    Boolean friendshipHasInInvitation(Long personIdInitiator, Long personIdReceiver);
}

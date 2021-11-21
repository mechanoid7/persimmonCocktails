package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.FriendshipInvitationDao;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendshipInvitationService {
    FriendshipInvitationDao friendshipInvitationDao;

    public void addFriendshipInvitation(Long personIdInitiator, Long personIdReceiver, String massage){
        if (personIdInitiator.equals(personIdReceiver)) throw new WrongCredentialsException("The user cannot invite himself to become friends.");
        friendshipInvitationDao.addFriendshipInvitation(personIdInitiator, personIdReceiver, massage);
    }

    public void deleteFriendshipInvitationById(Long personIdInitiator, Long personId){
        friendshipInvitationDao.deleteFriendshipInvitation(personIdInitiator, personId);
    }

    public List<FriendshipInvitationResponseDto> getPersonFriendshipInvitations(Long personId, Long pageNumber){
        return friendshipInvitationDao.getPersonFriendshipInvitations(personId, pageNumber);
    }
}

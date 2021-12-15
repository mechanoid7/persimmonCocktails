package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.FriendshipInvitationDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendshipInvitationService {
    FriendshipInvitationDao friendshipInvitationDao;
    PersonDao personDao;

    public void addFriendshipInvitation(Long personIdInitiator, Long personIdReceiver, String massage) {
        if (!personDao.existsById(personIdInitiator)) throw new NotFoundException("Person initiator");
        if (!personDao.existsById(personIdReceiver)) throw new NotFoundException("Person receiver");
        if (personIdInitiator.equals(personIdReceiver))
            throw new WrongCredentialsException("The user cannot invite himself to become friends.");
        if (friendshipInvitationDao.friendshipInvitationPairExists(personIdInitiator, personIdReceiver)){
            throw new DuplicateException("friendship invitation");
        }
        friendshipInvitationDao.addFriendshipInvitation(personIdInitiator, personIdReceiver, massage);
    }

    public void deleteFriendshipInvitationById(Long personIdInitiator, Long personId) {
        if (!personDao.existsById(personIdInitiator)) throw new NotFoundException("Person initiator");
        if (!personDao.existsById(personId)) throw new NotFoundException("Person receiver");
        friendshipInvitationDao.deleteFriendshipInvitation(personIdInitiator, personId);
    }

    public List<FriendshipInvitationResponseDto> getPersonFriendshipInvitations(Long personId, Long pageNumber) {
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return friendshipInvitationDao.getPersonFriendshipInvitations(personId, pageNumber);
    }

    public Long getPagesAmountFriendshipInvitations(Long personId) {
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        return friendshipInvitationDao.getPagesAmountFriendshipInvitations(personId);
    }
}

package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.FriendsDao;
import com.example.persimmoncocktails.dao.FriendshipInvitationDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.friend.FoundPersonsResponseDto;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FriendsService {
    private final FriendsDao friendsDao;
    private final PersonDao personDao;
    private final FriendshipInvitationDao friendshipInvitationDao;

    public FriendsService(FriendsDao friendsDao, PersonDao personDao, FriendshipInvitationDao friendshipInvitationDao) {
        this.friendsDao = friendsDao;
        this.personDao = personDao;
        this.friendshipInvitationDao = friendshipInvitationDao;
    }

    public List<FriendResponseDto> searchPersonsByNameSubstring(String substring, Long pageNumber) { // return list of persons by page, who is not an administrator and moderator
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return friendsDao.searchPersonsByNameSubstring("%" + substring + "%", pageNumber);
    }

    public List<FoundPersonsResponseDto> searchPersonsByNameSubstringWithoutFriends(Long personId, String substring, Long pageNumber) {
        // return list of persons by page, who is not an administrator and moderator and is not a friend
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return friendsDao.searchPersonsByNameSubstringWithoutFriends(personId, "%" + substring + "%", pageNumber);
    }

    public void removeFriendById(Long personIdInitiator, Long friendId) {
        if (!personDao.existsById(friendId)) throw new NotFoundException("Person");
        friendsDao.removeFriendById(personIdInitiator, friendId);
    }

    public List<FriendResponseDto> getPersonFriends(Long personId, Long pageNumber) {
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return friendsDao.getListFriendByNameSubstring(personId, "%%", pageNumber);
    }

    public List<FriendResponseDto> getListFriendsBySubstring(Long personId, String substring, Long pageNumber) {
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        if (substring.equals(""))
            return getPersonFriends(personId, pageNumber); // return list of friends without search
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        return friendsDao.getListFriendByNameSubstring(personId, "%" + substring + "%", pageNumber);
    }

    public void addFriend(Long personIdReceiver, Long personIdInitiator) {
        if (personIdInitiator.equals(personIdReceiver))
            throw new WrongCredentialsException("The user cannot have friendship with himself.");
        if (!friendshipInvitationDao.friendshipInvitationPairExists(personIdInitiator, personIdReceiver))
            throw new NotFoundException("friendship invitation");
        friendsDao.addFriend(personIdInitiator, personIdReceiver);
        friendshipInvitationDao.deleteFriendshipInvitation(personIdReceiver, personIdInitiator);
    }

    public Boolean isPersonsBySubstringWithoutFriendsExists(Long personId, String substring, Long pageNumber) {
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        if (pageNumber < 0) throw new IncorrectRangeNumberFormat("of page");
        return friendsDao.isPersonsBySubstringWithoutFriendsExists(personId, "%" + substring + "%", pageNumber);
    }

    public Long numberOfPagesPersonsBySubstringWithoutFriends(Long personId, String substring) {
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        return friendsDao.getNumberOfPagePersonsBySubstringWithoutFriends(personId, "%" + substring + "%");
    }

    public Long numberOfPagesFriendsBySubstring(Long personId, String substring) {
        if (!personDao.existsById(personId)) throw new NotFoundException("Person");
        return friendsDao.getNumberOfPageFriends(personId, "%" + substring + "%");
    }
}

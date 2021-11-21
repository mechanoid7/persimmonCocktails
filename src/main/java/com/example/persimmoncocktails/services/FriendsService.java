package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.FriendsDao;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.IncorrectRangeNumberFormat;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FriendsService {
    FriendsDao friendsDao;

    public List<FriendResponseDto> searchPersonsByNameSubstring(String substring, Long pageNumber) { // return list of persons by page, who is not an administrator and moderator
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        if (pageNumber<0) throw new IncorrectRangeNumberFormat("of page");
        return friendsDao.searchPersonsByNameSubstring("%"+substring+"%", pageNumber);
    }

    public void removeFriendById(Long personIdInitiator, Long friendId) {
        friendsDao.removeFriendById(personIdInitiator, friendId);
    }

    public List<FriendResponseDto> getPersonFriends(Long personId, Long pageNumber) {
        return friendsDao.getPersonFriends(personId, pageNumber);
    }

    public List<FriendResponseDto> getListFriendsBySubstring(Long personId, String substring, Long pageNumber) {
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        return friendsDao.getListFriendByNameSubstring(personId, "%"+substring+"%", pageNumber);
    }

    public void addFriend(Long personIdInitiator, Long personIdReciever){
        if (personIdInitiator.equals(personIdReciever)) throw new WrongCredentialsException("The user cannot have friendship with himself.");
        friendsDao.addFriend(personIdInitiator, personIdReciever);
    }
}

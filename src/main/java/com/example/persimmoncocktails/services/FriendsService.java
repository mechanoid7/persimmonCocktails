package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.FriendsDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class FriendsService {
    FriendsDao friendsDao;

    public List<FriendResponseDto> searchPersonsByNameSubstring(String substring) { // return fist of persons, who is not an administrator and moderator
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        return friendsDao.searchPersonsByNameSubstring("%"+substring+"%");
    }

    public void removeFriendById(Long personIdInitiator, Long friendId) {
        friendsDao.removeFriendById(personIdInitiator, friendId);
    }

    public List<FriendResponseDto> getPersonFriends(Long personId) {
        return friendsDao.getPersonFriends(personId);
    }

    public List<FriendResponseDto> getListFriendsBySubstring(Long personId, String substring) {
        if (!AuthorizationService.nameIsValid(substring)) throw new IncorrectNameFormat();
        return friendsDao.getListFriendByNameSubstring(personId, "%"+substring+"%");
    }

    public void addFriend(Long personIdInitiator, Long personIdReciever){
        if (personIdInitiator.equals(personIdReciever)) throw new WrongCredentialsException("The user cannot have friendship with himself.");
        friendsDao.addFriend(personIdInitiator, personIdReciever);
    }
}

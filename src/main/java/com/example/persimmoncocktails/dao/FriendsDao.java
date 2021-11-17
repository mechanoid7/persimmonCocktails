package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.models.Person;

import java.util.List;

public interface FriendsDao {
    List<FriendResponseDto> getPersonFriends(Long personId);

    List<FriendResponseDto> getListFriendByNameSubstring(Long personId, String substring);

    List<FriendResponseDto> searchPersonsByNameSubstring(String name);

    void removeFriendById(Long personIdInitiator, Long friendId);

    void addFriend(Long personIdInitiator, Long personIdReciever);
}

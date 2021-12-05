package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.friend.FoundPersonsResponseDto;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;

import java.util.List;

public interface FriendsDao {
//    List<FriendResponseDto> getPersonFriends(Long personId, Long pageNumber);

    List<FriendResponseDto> getListFriendByNameSubstring(Long personId, String substring, Long pageNumber);

    List<FriendResponseDto> searchPersonsByNameSubstring(String substring, Long pageNumber);

    List<FoundPersonsResponseDto> searchPersonsByNameSubstringWithoutFriends(Long personId, String substring, Long pageNumber);

    void removeFriendById(Long personIdInitiator, Long friendId);

    void addFriend(Long personIdInitiator, Long personIdReceiver);

    Boolean isPersonsBySubstringWithoutFriendsExists(Long personId, String substring, Long pageNumber);

    Long getNumberOfPagePersonsBySubstringWithoutFriends(Long personId, String substring);

    Long getNumberOfPageFriends(Long personId, String substring);
}

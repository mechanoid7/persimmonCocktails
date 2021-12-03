package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.FriendsDao;
import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.FriendMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySources({
        @PropertySource("classpath:sql/friends_queries.properties"),
        @PropertySource("classpath:var/general.properties")
})
@RequiredArgsConstructor
public class FriendsDaoImpl implements FriendsDao {

    private final FriendMapper friendMapper = new FriendMapper();
    private final JdbcTemplate jdbcTemplate;

//    @Value("${sql_friend_get_all_friends}")
//    private String sqlGetAllFriends;
    @Value("${sql_friend_get_all_friends_by_substring}")
    private String sqlGetListFriendBySubstring;
    @Value("${sql_friend_get_all_persons_by_substring}")
    private String sqlGetListUsersBySubstring;
    @Value("${sql_friend_delete_friendship}")
    private String sqlDeleteFriend;
    @Value("${sql_friend_add_friendship}")
    private String sqlAddFriendship;
    @Value("${sql_friend_get_all_persons_by_substring_without_friends}")
    private String sqlGetListUsersBySubstringWithoutFriends;
    @Value("${sql_friend_persons_by_substring_without_friends_exists}")
    private String sqlUsersBySubstringWithoutFriendsExists;
    @Value("${sql_friend_persons_count_by_substring_without_friends}")
    private String sqlPersonsCountBySubstringWithoutFriends;
    @Value("${sql_friend_count_friends_by_substring}")
    private String sqlFriendsCountBySubstring;
    @Value("${number_of_users_per_page}")
    private Long personsPerPage;

//    @Override
//    public List<FriendResponseDto> getPersonFriends(Long personId, Long pageNumber) { // get person friends by ID
//        return jdbcTemplate.query(sqlGetAllFriends, friendMapper, personId, personId,
//                pageNumber * personsPerPage, personsPerPage);
//    }

    @Override
    public List<FriendResponseDto> getListFriendByNameSubstring(Long personId, String substring, Long pageNumber) {
        return jdbcTemplate.query(sqlGetListFriendBySubstring, friendMapper, personId, personId,
                substring.toLowerCase(), pageNumber * personsPerPage, personsPerPage);
    }

    @Override
    public List<FriendResponseDto> searchPersonsByNameSubstring(String substring, Long pageNumber) {
        return jdbcTemplate.query(sqlGetListUsersBySubstring, friendMapper, substring.toLowerCase(),
                pageNumber * personsPerPage, personsPerPage);
    }

    @Override
    public List<FriendResponseDto> searchPersonsByNameSubstringWithoutFriends(Long personId, String substring, Long pageNumber) {
        return jdbcTemplate.query(sqlGetListUsersBySubstringWithoutFriends, friendMapper, personId, personId,
                substring.toLowerCase(), pageNumber * personsPerPage, personsPerPage);
    }

    @Override
    public void removeFriendById(Long personIdInitiator, Long friendId) {
        // there may be a subscription installation
        jdbcTemplate.update(sqlDeleteFriend, personIdInitiator, friendId, friendId, personIdInitiator);
    }

    @Override
    public void addFriend(Long personIdInitiator, Long personIdReceiver) {
        jdbcTemplate.update(sqlAddFriendship, personIdInitiator, personIdReceiver);
    }

    @Override
    public Boolean isPersonsBySubstringWithoutFriendsExists(Long personId, String substring, Long pageNumber) { // on page
        List<Long> personIds = jdbcTemplate.query(sqlUsersBySubstringWithoutFriendsExists,
                (rs, rowNum)-> rs.getLong("person_id"), personId, personId, substring.toLowerCase(),
                pageNumber * personsPerPage, personsPerPage);
        if (personIds.size()>1) return Boolean.TRUE;
        else return Boolean.FALSE;
    }

    @Override
    public Long getNumberOfPagePersonsBySubstringWithoutFriends(Long personId, String substring) {
        try {
            return (long) Math.ceil((double)jdbcTemplate.queryForObject(sqlPersonsCountBySubstringWithoutFriends, Long.class, personId, personId, substring)/personsPerPage);
        } catch (NullPointerException e){
            throw new UnknownException("Failed to get the number of users from the database.");
        }
    }

    @Override
    public Long getNumberOfPageFriends(Long personId, String substring) {
        try {
            return (long) Math.ceil((double)jdbcTemplate.queryForObject(sqlFriendsCountBySubstring, Long.class, personId, personId, substring)/personsPerPage);
        } catch (NullPointerException e){
            throw new UnknownException("Failed to get the number of friends from the database.");
        }
    }
}

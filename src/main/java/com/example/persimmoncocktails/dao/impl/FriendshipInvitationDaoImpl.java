package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.FriendshipInvitationDao;
import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import com.example.persimmoncocktails.mapper.FriendshipInvitationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
@PropertySources({
        @PropertySource("classpath:sql/friendship_invitations_queries.properties"),
        @PropertySource("classpath:var/general.properties")
})
@RequiredArgsConstructor
public class FriendshipInvitationDaoImpl implements FriendshipInvitationDao {

    private final JdbcTemplate jdbcTemplate;
    private final FriendshipInvitationMapper friendshipInvitationMapper = new FriendshipInvitationMapper();

    @Value("${sql_add_friendship_invitation}")
    private String sqlAddFriendshipInvitation;
    @Value("${sql_delete_friendship_invitation}")
    private String sqlDeleteFriendshipInvitation;
    @Value("${sql_get_all_friendship_invitations}")
    private String sqlGetPersonFriendshipInvitations;
    @Value("${sql_friend_invitation_exists}")
    private String sqlFriendshipInviteExists;
    @Value("${number_of_users_per_page}")
    private Long personsPerPage;

    @Override
    public void addFriendshipInvitation(Long personIdInitiator, Long personIdReceiver, String massage) {
        jdbcTemplate.update(sqlAddFriendshipInvitation, personIdInitiator, personIdReceiver, massage);
    }

    @Override
    public void deleteFriendshipInvitation(Long personIdInitiator, Long personId) {
        jdbcTemplate.update(sqlDeleteFriendshipInvitation, personIdInitiator, personId, personId, personIdInitiator);
    }

    @Override
    public List<FriendshipInvitationResponseDto> getPersonFriendshipInvitations(Long personId, Long pageNumber) {
        return jdbcTemplate.query(sqlGetPersonFriendshipInvitations, friendshipInvitationMapper, personId, pageNumber * personsPerPage, personsPerPage);
    }

    @Override
    public Boolean friendshipHasInInvitation(Long personIdInitiator, Long personIdReceiver) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlFriendshipInviteExists, Boolean.class, personIdInitiator, personIdReceiver));
    }
}

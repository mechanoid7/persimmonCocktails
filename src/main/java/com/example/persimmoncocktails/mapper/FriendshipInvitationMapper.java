package com.example.persimmoncocktails.mapper;

import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendshipInvitationMapper implements RowMapper<FriendshipInvitationResponseDto> {
    @Override
    public FriendshipInvitationResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = null;
        Long blogId = rs.getLong("blog_id");
        if(rs.wasNull()) blogId = null;
        return new FriendshipInvitationResponseDto(rs.getLong("person_id"),
                rs.getString("name"),
                rs.getString("message"),
                photoId, blogId,
                rs.getDate("date"));
    }
}

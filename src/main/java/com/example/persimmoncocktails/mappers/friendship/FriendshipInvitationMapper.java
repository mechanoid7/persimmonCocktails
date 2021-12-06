package com.example.persimmoncocktails.mappers.friendship;

import com.example.persimmoncocktails.dtos.friendshipInvitation.FriendshipInvitationResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class FriendshipInvitationMapper implements RowMapper<FriendshipInvitationResponseDto> {
    @Override
    public FriendshipInvitationResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = null;
        Long blogId = rs.getLong("blog_id");
        if(rs.wasNull()) blogId = null;
        LocalDateTime localDateTime = null;
        Timestamp timestamp = rs.getTimestamp("datetime");
        if(timestamp != null) localDateTime = timestamp.toLocalDateTime();
        return new FriendshipInvitationResponseDto(rs.getLong("person_id"),
                rs.getString("name"),
                rs.getString("message"),
                photoId,
                blogId,
                localDateTime);
    }
}

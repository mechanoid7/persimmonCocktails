package com.example.persimmoncocktails.mappers.friend;

import com.example.persimmoncocktails.dtos.friend.FriendResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FriendMapper implements RowMapper<FriendResponseDto> {
    @Override
    public FriendResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = null;
        Long blogId = rs.getLong("blog_id");
        if(rs.wasNull()) blogId = null;
        return new FriendResponseDto(rs.getLong("person_id"), rs.getString("name"), photoId, blogId);
    }
}

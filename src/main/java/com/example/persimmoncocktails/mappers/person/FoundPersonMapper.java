package com.example.persimmoncocktails.mappers.person;

import com.example.persimmoncocktails.dtos.friend.FoundPersonsResponseDto;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class FoundPersonMapper implements RowMapper<FoundPersonsResponseDto> {
    @Override
    public FoundPersonsResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = null;
        Long blogId = rs.getLong("blog_id");
        if(rs.wasNull()) blogId = null;
        return new FoundPersonsResponseDto(rs.getLong("person_id"),
                rs.getString("name"),
                photoId,
                blogId,
                rs.getBoolean("is_invited"));
    }
}

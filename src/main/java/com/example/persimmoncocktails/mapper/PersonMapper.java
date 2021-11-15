package com.example.persimmoncocktails.mapper;

import com.example.persimmoncocktails.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        Long photoId = rs.getLong("photo_id");
        if(rs.wasNull()) photoId = null;
        Long blogId = rs.getLong("blog_id");
        if(rs.wasNull()) blogId = null;
        Integer roleId = rs.getInt("role_id");
        if(rs.wasNull()) roleId = null;
        return new Person(
                rs.getLong("person_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                photoId,
                blogId,
                roleId
        );
    }
}

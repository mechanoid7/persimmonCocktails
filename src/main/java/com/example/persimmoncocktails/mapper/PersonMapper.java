package com.example.persimmoncocktails.mapper;

import com.example.persimmoncocktails.models.Person;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PersonMapper implements RowMapper<Person> {
    @Override
    public Person mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Person(
                rs.getLong("person_id"),
                rs.getString("name"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getLong("photo_id"),
                rs.getLong("blog_id"),
                rs.getInt("role_id"));
    }
}
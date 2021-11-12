package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.ModeratorDao;
import com.example.persimmoncocktails.mapper.PersonMapper;
import com.example.persimmoncocktails.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@PropertySource("classpath:sql/moderator_queries.properties")
@RequiredArgsConstructor
public class ModeratorDaoImpl implements ModeratorDao {

    @Value("${sql_moderator_get_all}")
    private String sqlGetAllModerators;

    private final JdbcTemplate jdbcTemplate;
    private final PersonMapper personMapper = new PersonMapper();

    @Override
    public List<Person> getAllModerators() {
        List<Person> result = jdbcTemplate.query(sqlGetAllModerators, personMapper);
        if (result.isEmpty())
            return new ArrayList<>();
        return result;
    }
}

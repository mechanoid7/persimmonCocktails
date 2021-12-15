package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.ModeratorDao;
import com.example.persimmoncocktails.mappers.person.PersonMapper;
import com.example.persimmoncocktails.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@PropertySource("classpath:sql/moderator_queries.properties")
@RequiredArgsConstructor
public class ModeratorDaoImpl implements ModeratorDao {

    @Value("${sql_moderator_get_all}")
    private String sqlGetAllModerators;
    @Value("${sql_moderator_with_such_id_exists}")
    private String sqlMethodWithSuchIdExists;

    private final JdbcTemplate jdbcTemplate;
    private final PersonMapper personMapper = new PersonMapper();

    @Override
    public List<Person> getAllModerators() {
        return jdbcTemplate.query(sqlGetAllModerators, personMapper);
    }

    @Override
    public boolean existsById(Long personId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlMethodWithSuchIdExists, Boolean.class, personId));
    }
}

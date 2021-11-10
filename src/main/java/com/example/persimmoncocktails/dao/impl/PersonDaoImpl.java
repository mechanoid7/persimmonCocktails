package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.mapper.PersonMapper;
import com.example.persimmoncocktails.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/person_queries.properties")
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {

    @Value("${sql_person_create}")
    private String sqlInsertNewPerson;
    @Value("${sql_person_read_by_id}")
    private String sqlReadPersonById;
    @Value("${sql_person_read_by_email}")
    private String sqlReadPersonByEmail;
    @Value("${sql_person_update}")
    private String sqlUpdatePerson;
    @Value("${sql_person_delete}")
    private String sqlDeletePerson;

    private final PersonMapper personMapper = new PersonMapper();

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void create(Person person) { // create new person
        jdbcTemplate.update(sqlInsertNewPerson, person.getName(), person.getEmail(), person.getPassword(),
                person.getPhotoId(), person.getBlogId(), person.getRoleId());
    }

    @Override
    public Person read(Long personId) { // read person by ID
        return jdbcTemplate.queryForObject(sqlReadPersonById, personMapper, personId);
    }

    @Override
    public Person read(String email) { // read person by email
        return jdbcTemplate.queryForObject(sqlReadPersonByEmail, personMapper, email);
    }

    @Override
    public void update(Person person) { // update person data
        jdbcTemplate.update(sqlUpdatePerson, person.getName(), person.getEmail(), person.getPassword(),
                person.getPhotoId(), person.getBlogId(), person.getRoleId(), person.getPersonId());
    }

    @Override
    public void delete(Long personId) { // delete person by ID
        jdbcTemplate.update(sqlDeletePerson, personId);
    }
}
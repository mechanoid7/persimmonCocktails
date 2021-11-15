package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mappers.PersonMapper;
import com.example.persimmoncocktails.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@PropertySource("classpath:sql/person_queries.properties")
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {

    private final PersonMapper personMapper = new PersonMapper();
    private final JdbcTemplate jdbcTemplate;
    @Value("${sql_person_create}")
    private String sqlInsertNewPerson;
    @Value("${sql_person_create_base}")
    private String sqlInserNewPersonRequiredFields;
    @Value("${sql_person_read_by_id}")
    private String sqlReadPersonById;
    @Value("${sql_person_read_by_email}")
    private String sqlReadPersonByEmail;
    @Value("${sql_person_update}")
    private String sqlUpdatePerson;
    @Value("${sql_person_delete}")
    private String sqlDeletePerson;

    @Override
    public void create(Person person) { // create new person
        try {
            jdbcTemplate.update(sqlInserNewPersonRequiredFields, person.getName(), person.getEmail(), person.getPassword(),
                    person.getRoleId());
        } catch (DuplicateKeyException e) {
            throw new DuplicateException("Person");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Person read(Long personId) { // read person by ID
        try {
            return jdbcTemplate.queryForObject(sqlReadPersonById, personMapper, personId);
        } catch (EmptyResultDataAccessException emptyE) {
//            throw new NotFoundException("Person");
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public Person readByEmail(String email) { // read person by email
        try {
            return jdbcTemplate.queryForObject(sqlReadPersonByEmail, personMapper, email);
        } catch (EmptyResultDataAccessException emptyE) {
//            throw new NotFoundException("Person");
            return null;
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void update(Person person) { // update person data
        // we should consider changing way to modify rows
        jdbcTemplate.update(sqlUpdatePerson, person.getName(), person.getEmail(), person.getPassword(),
                person.getPhotoId(), person.getBlogId(), person.getRoleId(), person.getPersonId());
    }

    @Override
    public void delete(Long personId) { // delete person by ID
        jdbcTemplate.update(sqlDeletePerson, personId);
    }
}

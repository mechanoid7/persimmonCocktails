package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.mapper.PersonMapper;
import com.example.persimmoncocktails.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

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
    @Value("${sql_person_get_all_friends}")
    private String sqlGetAllFriends;
    @Value("${sql_person_get_all_friends_by_substring}")
    private String sqlGetListFriendBySubstring;

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

    @Override
    public void changePassword(Long personId, String oldPassword, String newPassword) {
        Person person = read(personId);
        if (person != null && person.getPassword().equals(oldPassword)){ // compare old password input and DB
            person.setPassword(newPassword);
            update(person);
        }
    }

    @Override
    public List<Person> getPersonFriends(Long personId){ // get all person friends by ID
        List<Person> result = jdbcTemplate.query(sqlGetAllFriends, personMapper, personId, personId);
        if (result.isEmpty())
            return new ArrayList<>();
        return result;
    }

    @Override
    public List<Person> getListFriendBySubstring(Long personId, String substring){ // get all person friends by ID, filter by name
        List<Person> result = jdbcTemplate.query(sqlGetListFriendBySubstring, personMapper, personId, personId, substring.toLowerCase());
        if (result.isEmpty())
            return new ArrayList<>();
        return result;
    }
}

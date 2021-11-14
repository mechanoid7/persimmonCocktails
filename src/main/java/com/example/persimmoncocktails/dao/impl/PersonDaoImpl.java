package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.auth.RestorePasswordDataDto;
import com.example.persimmoncocktails.exceptions.DuplicateException;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.RecoverLinkExpired;
import com.example.persimmoncocktails.exceptions.UnknownException;
import com.example.persimmoncocktails.mapper.PersonMapper;
import com.example.persimmoncocktails.mapper.RestorePasswordMapper;
import com.example.persimmoncocktails.models.Person;
import lombok.RequiredArgsConstructor;
//import java.sql.time;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Repository
@PropertySource("classpath:sql/person_queries.properties")
@RequiredArgsConstructor
public class PersonDaoImpl implements PersonDao {

    private final PersonMapper personMapper = new PersonMapper();
    private final RestorePasswordMapper restorePasswordMapper = new RestorePasswordMapper();
    private final JdbcTemplate jdbcTemplate;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Value("${sql_person_with_such_id_exists}")
    private String sqlPersonWithSuchIdExists;
    @Value("${sql_person_create}")
    private String sqlInsertNewPerson;
    @Value("${sql_person_create_base}")
    private String sqlInsertNewPersonRequiredFields;
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
    @Value("${sql_person_save_recover_password_request}")
    private String sqlSaveRecoverPasswordRequest;
    @Value("${sql_person_data_dto_by_recovery_id}")
    private String sqlPersonIdByRequest;
    @Value("${sql_person_deactivate_password_change_request}")
    private String sqlDeactivateChangePasswordRequest;

    @Autowired
    public PersonDaoImpl(BCryptPasswordEncoder bCryptPasswordEncoder, JdbcTemplate jdbcTemplate) {
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public boolean existsById(Long personId) {
        return Boolean.TRUE.equals(jdbcTemplate.queryForObject(sqlPersonWithSuchIdExists, Boolean.class, personId));
    }

    @Override
    public void create(Person person) { // create new person
        try {
            jdbcTemplate.update(sqlInsertNewPersonRequiredFields, person.getName(), person.getEmail(), person.getPassword(),
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
        try {
            jdbcTemplate.update(sqlUpdatePerson, person.getName(), person.getEmail(), person.getPassword(),
                    person.getPhotoId(), person.getBlogId(), person.getRoleId(), person.getPersonId());
        }
        catch (DataIntegrityViolationException dataIntegrityViolationException){
            throw new NotFoundException("");
        } catch (DataAccessException rootException) {
            // we should log it
            rootException.printStackTrace();
            throw new UnknownException();
        }
    }

    @Override
    public void delete(Long personId) { // delete person by ID
        jdbcTemplate.update(sqlDeletePerson, personId);
    }

    @Override
    public void changePassword(Long personId, String newPassword) {

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

    @Override
    public void saveRecoverPasswordRequest(Long personId, LocalDateTime localDateTime, String hashedId) { // save request to db
        jdbcTemplate.update(sqlSaveRecoverPasswordRequest, personId, localDateTime, hashedId);
    }

    @Override
    public List<RestorePasswordDataDto> restorePassword(String id, Long personId) { // get user requests
        return jdbcTemplate.query(sqlPersonIdByRequest, restorePasswordMapper, personId);
    }

    @Override
    public void deactivateRequestsBuPersonId(Long personId){ // deactivate all this user request
        jdbcTemplate.update(sqlDeactivateChangePasswordRequest, personId);
    }
}

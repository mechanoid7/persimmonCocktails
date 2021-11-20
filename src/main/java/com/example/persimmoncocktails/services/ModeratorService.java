package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.ModeratorDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import com.example.persimmoncocktails.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ModeratorService {
    PersonDao personDao;
    ModeratorDao moderatorDao;
    PasswordEncoder passwordEncoder;

    @Autowired
    public ModeratorService(PersonDao personDao, ModeratorDao moderatorDao, PasswordEncoder passwordEncoder) {
        this.personDao = personDao;
        this.moderatorDao = moderatorDao;
        this.passwordEncoder = passwordEncoder;
    }

    public void updateName(Long personId, String name) {
        if(!personDao.existsById(personId)) throw new NotFoundException("Moderator");
        if(!AuthorizationService.nameIsValid(name)) throw new IncorrectNameFormat();
        Person person = personDao.read(personId);
        person.setName(name);
        personDao.update(person);
    }

    public void updatePhotoId(Long personId, Long photoId) {
        if(!personDao.existsById(personId)) throw new NotFoundException("Moderator");
        Person person = personDao.read(personId);
        person.setPhotoId(photoId);
        try {
            personDao.update(person);
        }
        catch (NotFoundException notFoundException){
            throw new NotFoundException("Photo");
        }
    }

    public PersonResponseDto readModeratorById(Long personId) {
        Person person = personDao.read(personId);
        if(person == null) throw new NotFoundException("Moderator");
        return PersonResponseDto.toDto(person);
    }

    public List<PersonResponseDto> getAllModerators() {
        return moderatorDao.getAllModerators().stream()
                .map(PersonResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public void changePassword(Long personId, String oldPassword, String newPassword) {
        Person person = personDao.read(personId);
        if (person != null &&
                passwordEncoder.matches(oldPassword, person.getPassword())){ // compare old password input and DB
            person.setPassword(passwordEncoder.encode(newPassword));
            personDao.update(person);
        }
        else{
            throw new WrongCredentialsException();
        }
    }
}

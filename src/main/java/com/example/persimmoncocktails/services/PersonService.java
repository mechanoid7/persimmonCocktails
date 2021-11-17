package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.person.PersonResponseDto;
import com.example.persimmoncocktails.exceptions.IncorrectNameFormat;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PersonService {
    PersonDao personDao;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void delete(Long personId) {
        if(!personDao.existsById(personId)) throw new NotFoundException("Person");
        personDao.delete(personId);
    }

    public void changePassword(Long personId, String oldPassword, String newPassword) {
        Person person = personDao.read(personId);
        if (person != null &&
                bCryptPasswordEncoder.matches(oldPassword, person.getPassword())){ // compare old password input and DB
            person.setPassword(bCryptPasswordEncoder.encode(newPassword));
            personDao.update(person);
        }
        else{
            throw new WrongCredentialsException();
        }
    }

    public PersonResponseDto readByEmail(String personEmail) {
        Person person = personDao.readByEmail(personEmail);
        if(person == null) throw new NotFoundException("Person");
        return PersonResponseDto.toDto(person);
    }

    public PersonResponseDto readPersonById(Long personId) {
        Person person = personDao.read(personId);
        if(person == null) throw new NotFoundException("Person");
        return PersonResponseDto.toDto(person);
    }

    public void updateName(Long personId, String name) {
        if(!personDao.existsById(personId)) throw new NotFoundException("Person");
        if(!AuthorizationService.nameIsValid(name)) throw new IncorrectNameFormat();
        Person person = personDao.read(personId);
        person.setName(name);
        personDao.update(person);
    }

    public void updatePhotoId(Long personId, Long photoId) {
        if(!personDao.existsById(personId)) throw new NotFoundException("Person");
        Person person = personDao.read(personId);
        person.setPhotoId(photoId);
        try {
            personDao.update(person);
        }
        catch (NotFoundException notFoundException){
            throw new NotFoundException("Photo");
        }
    }
}

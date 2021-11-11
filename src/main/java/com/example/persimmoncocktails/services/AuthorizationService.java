package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.controllers.PersonController;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dao.impl.PersonDaoImpl;
import com.example.persimmoncocktails.dtos.ResponseMessage;
import com.example.persimmoncocktails.dtos.auth.RequestRegistrationDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestSigninDataDto;
import com.example.persimmoncocktails.exceptions.IncorrectEmailFormat;
import com.example.persimmoncocktails.exceptions.IncorrectPasswordFormat;
import com.example.persimmoncocktails.exceptions.WrongCredentialsException;
import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class AuthorizationService {
    PersonDao personDao;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public void authorizeUser(RequestSigninDataDto signinData){
        Person person = personDao.read(signinData.getEmail());
        if(person != null) {
            if(!bCryptPasswordEncoder.matches(signinData.getPassword(), person.getPassword())) {
                throw new WrongCredentialsException();
            }
        }
        else {
            throw new WrongCredentialsException();
        }


        // generate and send token
    }

    public Long registerUser(RequestRegistrationDataDto registrationData) {

        if(!emailIsValid(registrationData.getEmail())){
            throw new IncorrectEmailFormat();
        }
        if(!passwordIsValid(registrationData.getPassword())){
            throw new IncorrectPasswordFormat();
        }

        Person person = Person.builder()
                .name(registrationData.getName())
                .password(hashPassword(registrationData.getPassword()))
                .email(registrationData.getEmail())
                .roleId(3)
                .build();// default user

        personDao.create(person);

        return personDao.read(person.getEmail()).getPersonId();
    }

    public void logoutUser() {
        //end session, delete cookies
        // disable access token
//        return ResponseEntity.ok("user logged out");
    }

    public ResponseMessage recoverPassword(String email) {
        Person person = personDao.read(email);
        if(person != null) {
            //send link
        }
        return new ResponseMessage("If this user with such email exists, he/she will be contacted via email");
    }

    public boolean passwordIsValid(String password) {
        String regex = "^(?=.*[0-9])"
                     + "(?=.*[a-z])"
                     + "(?=.*[A-Z])"
                     + "(?=\\S+$).{8,20}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public static boolean emailIsValid(String email) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    private String hashPassword(String password) {

        return bCryptPasswordEncoder.encode(password);
    }

}

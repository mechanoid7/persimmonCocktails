package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.auth.RequestRegistrationDataDto;
import com.example.persimmoncocktails.dtos.auth.RequestSigninDataDto;
import com.example.persimmoncocktails.exceptions.*;
import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@PropertySource("classpath:var/general.properties")
public class AuthorizationService {
    PersonDao personDao;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    public final JavaMailSender emailSender;

    @Value("${site_url}")
    private String siteUrl;

    @Autowired
    public AuthorizationService(JavaMailSender emailSender, BCryptPasswordEncoder bCryptPasswordEncoder, PersonDao personDao) {
        this.emailSender = emailSender;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.personDao = personDao;
    }

    public Long authorizeUser(RequestSigninDataDto signinData){
        Person person = personDao.readByEmail(signinData.getEmail());
        if(person != null) {
            if(!bCryptPasswordEncoder.matches(signinData.getPassword(), person.getPassword())) {
                throw new WrongCredentialsException();
            }
        }
        else {
            throw new WrongCredentialsException();
        }


        // generate and send token
        return person.getPersonId();
    }

    public Long registerUser(RequestRegistrationDataDto registrationData) {
        if(!nameIsValid(registrationData.getName())){
            throw new IncorrectNameFormat();
        }
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

        return personDao.readByEmail(person.getEmail()).getPersonId();
    }

    public void logoutUser() {
        //end session, delete cookies
        // disable access token
//        return ResponseEntity.ok("user logged out");
    }

    public void forgotPassword(String email) { // send recover link on email
        Person person = personDao.readByEmail(email);
        if(person != null) {
            String id = generateRandomString();
            String hashedId = hashPassword(id);

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("Persimmon password recover");
            message.setText("To change your password, follow the link " + generatePasswordRecoveryLink(id, person.getPersonId()) +
                    "\nIf you have not requested to change your password, simply ignore this message. ");

            this.emailSender.send(message);

            personDao.saveRecoverPasswordRequest(person.getPersonId(), LocalDateTime.now(), hashedId);
        }
        else throw new NotFoundException("Email");
    }

    public void recoverPassword(String id, Long personId, String newPassword) {
        personDao.restorePassword(id, personId, hashPassword(newPassword));
    }

    public static boolean passwordIsValid(String password) { // method is correct?
        return true;
//        String regex = "^(?=.*[0-9])"
//                + "(?=.*[a-z])"
//                + "(?=.*[A-Z])"
//                + "(?=\\S+$){8,20}$";
//
//        Pattern pattern = Pattern.compile(regex);
//        Matcher matcher = pattern.matcher(password);
//
//        return matcher.matches();
    }

    public static boolean emailIsValid(String email) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    public static boolean nameIsValid(String name){
        String regex = "^[a-zA-Z0-9 ]{3,255}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.find();
    }

    private String hashPassword(String password) {

        return bCryptPasswordEncoder.encode(password);
    }

}

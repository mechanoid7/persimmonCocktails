package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.controllers.PersonController;
import com.example.persimmoncocktails.dao.impl.PersonDaoImpl;
import com.example.persimmoncocktails.models.Person;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthorizationService {
    PersonDaoImpl personDao;
    PersonController personController;
    public AuthorizationService() {

    }

    public ResponseEntity authorizeUser(String email, String password){
        Person person = personDao.read(email);
        if(person != null) {
            if(password != person.getPassword()) {
                return ResponseEntity.ok("wrong password");  // return error
            }
        }
        else {
            return ResponseEntity.ok("user not found");      // return error
        }
        return ResponseEntity.ok(person);
    }

    public ResponseEntity registerUser(String email, String password) {

        if(!emailIsValid(email)) {
            return ResponseEntity.ok("email is not valid");
        }
        if(!passwordIsValid(password)) {
            return ResponseEntity.ok("password is not valid");
        }

        Person person = new Person();
        person.setEmail(email);
        person.setPassword(password);

        personDao.create(person);

        return ResponseEntity.ok(person);
    }

    public ResponseEntity logoutUser() {
        //end session, delete cookies
        return ResponseEntity.ok("user logged out");
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

    public ResponseEntity recoverPassword(String email) {
        //send link
        return null;
    }

}

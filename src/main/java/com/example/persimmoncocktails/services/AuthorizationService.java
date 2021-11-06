package com.example.persimmoncocktails.services;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class AuthorizationService {
    private final MailService mailService;

    public AuthorizationService(MailService mailService) {
        this.mailService = mailService;
    }

    public ResponseEntity authorizeUser(String username, String password){  //change ResponseEntity on user
        //check credentials in DB
        return ResponseEntity.ok("user authorized"); //return user
    }

    public ResponseEntity registerUser(String username, String password, String email) {

        try {
            mailService.sendEmail();           //email validation
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        //validate entered email verification code

        if(!passwordIsValid(password)) {
            //return message "password should contain ... "
        }

        return ResponseEntity.ok("successful registration");  //return user
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

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    public ResponseEntity recoverPassword(String email) {
        //send link
        return null;
    }

}

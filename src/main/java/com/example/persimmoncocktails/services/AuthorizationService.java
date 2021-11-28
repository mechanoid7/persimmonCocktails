package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.configurations.PersonRolesConfig;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.auth.RequestRegistrationDataDto;
import com.example.persimmoncocktails.dtos.auth.RestorePasswordDataDto;
import com.example.persimmoncocktails.exceptions.*;
import com.example.persimmoncocktails.models.Person;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
@PropertySource("classpath:var/general.properties")
public class AuthorizationService implements UserDetailsService{
    PersonDao personDao;
    PasswordEncoder passwordEncoder;

    private final JavaMailSender emailSender;

    @Value("${site_url}")
    private String siteUrl;
    @Value("${link_restore_password_lifetime}")
    private Integer linkRestorePasswordLifetime;

    @Autowired
    public AuthorizationService(JavaMailSender emailSender, PasswordEncoder passwordEncoder, PersonDao personDao) {
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
        this.personDao = personDao;
    }

    public void registerUser(RequestRegistrationDataDto registrationData) {
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
                .roleId(PersonRolesConfig.clientRoleId)
                .isActive(true)
                .build();// default user

        personDao.create(person);

        personDao.readByEmail(person.getEmail());
    }

    public void forgotPassword(String email) { // send recover link on email
        Person person = personDao.readByEmail(email);
        if(person != null) {
            String id = generateRandomString();
            String hashedId = hashPassword(id);

            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(email);
            message.setSubject("Persimmon cocktails password recover");
            message.setText("To change your password, follow the link " + generatePasswordRecoveryLink(id, person.getPersonId()) +
                    "\nIf you have not requested to change your password, simply ignore this message. ");

            this.emailSender.send(message);

            personDao.saveRecoverPasswordRequest(person.getPersonId(), LocalDateTime.now(), hashedId);
        }
        else throw new NotFoundException("Email");
    }

    public void recoverPassword(String id, Long personId, String newPassword) {
        if(!passwordIsValid(newPassword)) throw new IncorrectPasswordFormat();

        List<RestorePasswordDataDto> dataDto = personDao.restorePassword(personId);
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (dataDto.isEmpty()) throw new NotFoundException("Request for password restore");

        boolean updatePerson = false;
        for (RestorePasswordDataDto dto : dataDto){
            if (matchHash(id, dto.getId()) &&
                    ChronoUnit.HOURS.between(dto.getLocalDateTime(), currentDateTime) < linkRestorePasswordLifetime){ // if id matched and the request has not timed out
                Person person = personDao.read(dto.getPersonId());
                person.setPassword(hashPassword(newPassword));
                personDao.update(person);
                updatePerson = true;
                break;
            }
        }
        if (!updatePerson) throw new LinkExpired("Recover");

        personDao.deactivateRequestsByPersonId(personId);
    }

    public static boolean passwordIsValid(String password) {
        String regex = "^(?=.[a-z])(?=.[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        return Pattern.compile(regex).matcher(password).matches();
    }

    public static boolean emailIsValid(String email) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
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
        return passwordEncoder.encode(password);
    }

    private String generatePasswordRecoveryLink(String id, Long personId){
        return siteUrl+"/recover-password?id="+id+"&nn="+personId;
    }

    public static String generateRandomString(){
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 40;
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private boolean matchHash(String hash1, String hash2){
        return passwordEncoder.matches(hash1, hash2);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Person person = personDao.readByEmail(s);
        if(person == null) throw new UsernameNotFoundException(s);
        return person;
    }
}

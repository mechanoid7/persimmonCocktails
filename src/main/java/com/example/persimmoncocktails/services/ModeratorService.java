package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.ModeratorDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.auth.RestorePasswordDataDto;
import com.example.persimmoncocktails.dtos.person.ModeratorResponseDto;
import com.example.persimmoncocktails.exceptions.*;
import com.example.persimmoncocktails.models.Person;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.example.persimmoncocktails.services.AuthorizationService.*;

@Service
@PropertySource("classpath:var/general.properties")
public class ModeratorService {
    private final PersonDao personDao;
    private final ModeratorDao moderatorDao;
    private final PasswordEncoder passwordEncoder;

    @Value("${role_moderator_id}")
    private Integer roleModeratorId;
    @Value("${site_url}")
    private String siteUrl;
    @Value("${link_create_moderator_password_lifetime}")
    private Integer linkCreatePasswordLifetime;

    private final JavaMailSender emailSender;

    public ModeratorService(JavaMailSender emailSender, PasswordEncoder passwordEncoder, PersonDao personDao, ModeratorDao moderatorDao) {
        this.emailSender = emailSender;
        this.passwordEncoder = passwordEncoder;
        this.personDao = personDao;
        this.moderatorDao = moderatorDao;
    }

    public void updateName(Long personId, String name) {
        if (!moderatorDao.existsById(personId)) throw new NotFoundException("Moderator");
        if (!AuthorizationService.nameIsValid(name)) throw new IncorrectNameFormat();
        Person person = personDao.read(personId);
        person.setName(name);
        personDao.update(person);
    }

    public void updatePhotoId(Long personId, Long photoId) {
        if (!moderatorDao.existsById(personId)) throw new NotFoundException("Moderator");
        Person person = personDao.read(personId);
        person.setPhotoId(photoId);
        try {
            personDao.update(person);
        } catch (NotFoundException notFoundException) {
            throw new NotFoundException("Photo");
        }
    }

    public ModeratorResponseDto readModeratorById(Long personId) {
        Person person = personDao.read(personId);
        if (person == null) throw new NotFoundException("Moderator");
        return ModeratorResponseDto.toDto(person);
    }

    public List<ModeratorResponseDto> getAllModerators() {
        return moderatorDao.getAllModerators().stream()
                .map(ModeratorResponseDto::toDto)
                .collect(Collectors.toList());
    }

    public void changePassword(Long personId, String oldPassword, String newPassword) {
        if (!passwordIsValid(newPassword)) throw new IncorrectPasswordFormat();
        Person person = personDao.read(personId);
        if (person != null &&
                passwordEncoder.matches(oldPassword, person.getPassword())) { // compare old password input and DB
            person.setPassword(passwordEncoder.encode(newPassword));
            personDao.update(person);
        } else {
            throw new WrongCredentialsException();
        }
    }

    public void create(String name, String email) {
        if (!nameIsValid(name)) {
            throw new IncorrectNameFormat();
        }
        if (!emailIsValid(email)) {
            throw new IncorrectEmailFormat();
        }

        Person person = personDao.readByEmail(email);

        if (person == null) { // if new user
            person = Person.builder()
                    .name(name)
                    .email(email)
                    .password(hashPassword(generateRandomString())) // create random pass
                    .roleId(roleModeratorId)
                    .isActive(false)
                    .build();
            personDao.create(person);
            person.setPersonId(personDao.readByEmail(email).getPersonId()); // set personId for current object

        } else if (Objects.equals(person.getRoleId(), roleModeratorId)) { // if user exists and have moderator role
            person.setName(name);
            personDao.update(person);
        } else { // if user exists and haven't moderator role
            throw new RoleException();
        }

        String id = generateRandomString();
        String hashedId = hashPassword(id);

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Invitation to become a moderator in Persimmon cocktails");
        message.setText("The administrator has assigned you the role of moderator in the Persimmon Cocktails service. " +
                "To complete registration, follow the link to create a password.\n" + generatePasswordCreateLink(id, person.getPersonId()));

        this.emailSender.send(message);

        personDao.saveRecoverPasswordRequest(person.getPersonId(), LocalDateTime.now(), hashedId);
    }

    public void createPassword(String id, Long personId, String newPassword) {
        if (!passwordIsValid(newPassword)) {
            throw new IncorrectPasswordFormat();
        }

        List<RestorePasswordDataDto> dataDto = personDao.restorePassword(personId);
        LocalDateTime currentDateTime = LocalDateTime.now();

        if (dataDto.isEmpty()) throw new NotFoundException("Request for password create");

        boolean updatePerson = false;
        for (RestorePasswordDataDto dto : dataDto) {
            if (matchHash(id, dto.getId()) &&
                    ChronoUnit.HOURS.between(dto.getLocalDateTime(), currentDateTime) < linkCreatePasswordLifetime) { // if id matched and the request has not timed out
                Person person = personDao.read(dto.getPersonId());
                person.setPassword(hashPassword(newPassword));
                person.setIsActive(true);
                personDao.update(person);
                updatePerson = true;
                break;
            }
        }
        if (!updatePerson) throw new LinkExpired("Create");

        personDao.deactivateRequestsByPersonId(personId);
    }

    private boolean matchHash(String hash1, String hash2) {
        return passwordEncoder.matches(hash1, hash2);
    }

    private String hashPassword(String password) {
        return passwordEncoder.encode(password);
    }

    private String generatePasswordCreateLink(String id, Long personId) {
        return siteUrl + "/moderator/create-password?id=" + id + "&nn=" + personId;
    }

    public void changeStatus(Long personId) {
        if (personDao.personIsActive(personId))
            personDao.deactivatePersonByPersonId(personId);
        else personDao.activatePersonByPersonId(personId); // if disabled
    }
}

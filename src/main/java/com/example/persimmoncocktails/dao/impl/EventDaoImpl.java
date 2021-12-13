package com.example.persimmoncocktails.dao.impl;

import com.example.persimmoncocktails.dao.EventDao;
import com.example.persimmoncocktails.dao.ModeratorDao;
import com.example.persimmoncocktails.dtos.event.PersonInEventResponseDto;
import com.example.persimmoncocktails.dtos.event.RequestCreateEventDto;
import com.example.persimmoncocktails.dtos.event.ResponseEventDto;
import com.example.persimmoncocktails.mappers.person.PersonMapper;
import com.example.persimmoncocktails.models.Person;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@PropertySource("classpath:sql/event_queries.properties")
@RequiredArgsConstructor
public class EventDaoImpl implements EventDao {

    @Value("${sql_event_create}")
    private String sqlCreateEvent;
    @Value("${sql_event_get_by_id}")
    private String sqlGetById;

    private final JdbcTemplate jdbcTemplate;
//    private final PersonMapper personMapper = new PersonMapper();


    @Override
    public ResponseEventDto createEvent(RequestCreateEventDto event, Long personId) {
        Long eventId = jdbcTemplate.queryForObject(sqlCreateEvent, Long.class, event.getName(), event.getDescription(), LocalDateTime.now(), event.getPlace(), personId);
        return getById(eventId);
    }

    @Override
    public ResponseEventDto getById(Long eventId) {
        return null;
    }

    @Override
    public Boolean eventExists(Long eventId) {
        return null;
    }

    @Override
    public void disableEvent(Long eventId, Long personId) {

    }

    @Override
    public List<ResponseEventDto> getMemberEvents(Long personId, Long pageNumber) {
        return null;
    }

    @Override
    public List<ResponseEventDto> getOwnerEvents(Long personId, Long pageNumber) {
        return null;
    }

    @Override
    public void updateImage(Long eventId, Long personId, Long imageId) {

    }

    @Override
    public void inviteToEvent(Long eventId, Long personId, Long receiverId) {

    }

    @Override
    public void leaveFromEvent(Long eventId, Long personId) {

    }

    @Override
    public void removeCocktailFromEvent(Long eventId, Long personId, Long cocktailId) {

    }

    @Override
    public void addCocktailToEvent(Long eventId, Long personId, Long cocktailId) {

    }

    @Override
    public void declineEvent(Long eventId, Long personId) {

    }

    @Override
    public void acceptEvent(Long eventId, Long personId) {

    }

    @Override
    public List<PersonInEventResponseDto> getEventMembersByEventId(Long eventId, Long pageNumber) {
        return null;
    }
}

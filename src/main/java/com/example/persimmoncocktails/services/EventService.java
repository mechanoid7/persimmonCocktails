package com.example.persimmoncocktails.services;

import com.example.persimmoncocktails.dao.EventDao;
import com.example.persimmoncocktails.dao.ImageDao;
import com.example.persimmoncocktails.dao.PersonDao;
import com.example.persimmoncocktails.dtos.event.PersonInEventResponseDto;
import com.example.persimmoncocktails.dtos.event.RequestCreateEventDto;
import com.example.persimmoncocktails.dtos.event.ResponseEventDto;
import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.exceptions.NotFoundException;
import com.example.persimmoncocktails.exceptions.NullException;
import com.example.persimmoncocktails.models.image.ImageResponse;
import com.google.gson.Gson;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
public class EventService {

    private final EventDao eventDao;
    private final PersonDao personDao;

    public EventService(EventDao eventDao, PersonDao personDao) {
        this.eventDao = eventDao;
        this.personDao = personDao;
    }


    public void createEvent(RequestCreateEventDto requestCreateEvent, Long personId) {
    }


    public void disableEvent(Long eventId, Long personId) {
    }

    public List<ResponseEventDto> getMemberEvents(Long personId, Long pageNumber) {
        return null;
    }

    public List<ResponseEventDto> getOwnerEvents(Long personId, Long pageNumber) {
        return null;
    }

    public void updateImage(Long eventId, Long personId, Long imageId) {
    }

    public void inviteToEvent(Long eventId, Long personId, Long receiverId) {
    }

    public void leaveFromEvent(Long eventId, Long personId) {
    }

    public void removeCocktailFromEvent(Long eventId, Long personId, Long cocktailId) {
    }

    public void addCocktailToEvent(Long eventId, Long personId, Long cocktailId) {
    }

    public void declineEvent(Long eventId, Long personId) {
    }

    public void acceptEvent(Long eventId, Long personId) {
    }

    public List<PersonInEventResponseDto> getEventMembersByEventId(Long eventId, Long pageNumber) {
    }
}

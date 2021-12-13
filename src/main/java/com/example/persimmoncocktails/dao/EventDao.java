package com.example.persimmoncocktails.dao;

import com.example.persimmoncocktails.dtos.cocktail.BasicCocktailDto;
import com.example.persimmoncocktails.dtos.cocktail.FullCocktailDto;
import com.example.persimmoncocktails.dtos.cocktail.RequestCocktailUpdate;
import com.example.persimmoncocktails.dtos.cocktail.RequestCreateCocktail;
import com.example.persimmoncocktails.dtos.event.PersonInEventResponseDto;
import com.example.persimmoncocktails.dtos.event.RequestCreateEventDto;
import com.example.persimmoncocktails.dtos.event.ResponseEventDto;
import com.example.persimmoncocktails.models.cocktail.CocktailCategory;

import java.util.List;

public interface EventDao {
    ResponseEventDto createEvent(RequestCreateEventDto event, Long personId);

    Boolean eventExists(Long eventId);

    ResponseEventDto getById(Long eventId);

    void disableEvent(Long eventId, Long personId);

    List<ResponseEventDto> getMemberEvents(Long personId, Long pageNumber);

    List<ResponseEventDto> getOwnerEvents(Long personId, Long pageNumber);

    void updateImage(Long eventId, Long personId, Long imageId);

    void inviteToEvent(Long eventId, Long personId, Long receiverId);

    void leaveFromEvent(Long eventId, Long personId);

    void removeCocktailFromEvent(Long eventId, Long personId, Long cocktailId);

    void addCocktailToEvent(Long eventId, Long personId, Long cocktailId);

    void declineEvent(Long eventId, Long personId);

    void acceptEvent(Long eventId, Long personId);

    List<PersonInEventResponseDto> getEventMembersByEventId(Long eventId, Long pageNumber);
}
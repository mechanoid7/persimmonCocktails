package com.example.persimmoncocktails.controllers;

import com.example.persimmoncocktails.dtos.event.PersonInEventResponseDto;
import com.example.persimmoncocktails.dtos.event.RequestCreateEventDto;
import com.example.persimmoncocktails.dtos.event.ResponseEventDto;
import com.example.persimmoncocktails.dtos.image.ImageResponseDto;
import com.example.persimmoncocktails.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event")
@PreAuthorize("isAuthenticated")
public class EventController {

    private final EventService eventService;

    @Autowired
    public EventController(EventService eventService) {
        this.eventService = eventService;
    }


    @PostMapping(value = "/create")
    public void upload(@RequestBody RequestCreateEventDto requestCreateEvent){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.createEvent(requestCreateEvent, personId);
    }

    @GetMapping(value = "/member-events")
    public List<ResponseEventDto> getEventsByPageIfMember(@RequestParam("page") Long pageNumber){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return eventService.getMemberEvents(personId, pageNumber);
    }

    @GetMapping(value = "/owner-events")
    public List<ResponseEventDto> getEventsByPageIfOwner(@RequestParam("page") Long pageNumber){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        return eventService.getOwnerEvents(personId, pageNumber);
    }

    @GetMapping(value = "/event-members")
    public List<PersonInEventResponseDto> getEventMembersByEventId(@RequestParam("page") Long pageNumber, @RequestBody Long eventId){
        return eventService.getEventMembersByEventId(eventId, pageNumber);
    }

    @PostMapping(value = "/disable")
    public void disableEvent(@RequestBody Long eventId){ // decline event
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.disableEvent(eventId, personId);
    }

    @PostMapping(value = "/accept")
    public void acceptEvent(@RequestBody Long eventId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.acceptEvent(eventId, personId);
    }

    @PostMapping(value = "/decline")
    public void declineEvent(@RequestBody Long eventId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.declineEvent(eventId, personId);
    }

    @PostMapping(value = "/add-cocktail-to-event")
    public void addCocktailToEvent(@RequestBody Long eventId, @RequestBody Long cocktailId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.addCocktailToEvent(eventId, personId, cocktailId);
    }

    @DeleteMapping(value = "/remove-cocktail-from-event")
    public void removeCocktailFromEvent(@RequestBody Long eventId, @RequestBody Long cocktailId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.removeCocktailFromEvent(eventId, personId, cocktailId);
    }

    @PostMapping(value = "/invite-to-event")
    public void inviteToEvent(@RequestBody Long eventId, @RequestBody Long cocktailId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.inviteToEvent(eventId, personId, cocktailId);
    }

    @PostMapping(value = "/leave")
    public void leaveFromEvent(@RequestBody Long eventId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.leaveFromEvent(eventId, personId);
    }

    @PostMapping(value = "/update-image")
    public void updateImageEvent(@RequestBody Long eventId, @RequestBody Long imageId){
        Long personId = (Long) (SecurityContextHolder.getContext().getAuthentication().getDetails());
        eventService.updateImage(eventId, personId, imageId);
    }
}

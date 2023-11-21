package com.VibezVenue.controller;


import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import com.VibezVenue.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/event")
public class EventController {

    private final EventService eventService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveEvent(@RequestBody EventRequest eventRequest){

        eventService.saveEvent(eventRequest);
        return "Event is saved successfully!";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> getAllEvents(){

        return eventService.getAllEvents();
    }

    @GetMapping("/{eventid}")
    @ResponseStatus(HttpStatus.OK)
    public EventResponse getEventId(@PathVariable("eventid")String eventId){

        return eventService.getEventById(eventId);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String updateEvent(@RequestBody EventRequest eventRequest){

        eventService.saveEvent(eventRequest);
        return "Event is updated successfully!";
    }

}

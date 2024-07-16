package com.VibezVenue.controller;


import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import com.VibezVenue.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public List<EventResponse> findAllEvents(){

        return eventService.getAllEvents();
    }

    @GetMapping("/{eventcode}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<EventResponse> findEventByCode(@PathVariable("eventcode")String eventCode){

        return Optional.ofNullable(eventService.getEventByCode(eventCode));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String updateEvent(@RequestBody EventRequest eventRequest){

        eventService.saveEvent(eventRequest);
        return "Event is updated successfully!";
    }

}

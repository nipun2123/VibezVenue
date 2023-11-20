package com.VibezVenue.controller;


import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
}

package com.VibezVenue.controller;

import com.VibezVenue.EventServiceProxy;
import com.VibezVenue.dto.EventResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/event")
public class UserController {

    private final EventServiceProxy eventServiceProxy;

    @PostMapping
    public String bookEvent(){
        return "Event Booked";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> viewAllEvent(){
        return eventServiceProxy.findAllEvents();
    }

    @GetMapping("/{eventcode}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<EventResponse> findEventByCode(@PathVariable("eventcode")String eventCode){

        return eventServiceProxy.findEventByCode(eventCode);
    }

}
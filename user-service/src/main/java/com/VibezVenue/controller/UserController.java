package com.VibezVenue.controller;

import com.VibezVenue.EventServiceProxy;
import com.VibezVenue.dto.EventResponse;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user/event")
public class UserController {

    private final EventServiceProxy eventServiceProxy;

    @PostMapping
    @CircuitBreaker(name = "user")
    public String bookEvent(){
        return "Event Booked";
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "user", fallbackMethod = "fallBackMethod")
    @Retry(name = "user")
    public List<EventResponse> viewAllEvent(){
        return eventServiceProxy.findAllEvents();
    }

    @GetMapping("/{eventcode}")
    @ResponseStatus(HttpStatus.OK)
    @CircuitBreaker(name = "user")
    public Optional<EventResponse> findEventByCode(@PathVariable("eventcode")String eventCode){

        return eventServiceProxy.findEventByCode(eventCode);
    }

    public List<EventResponse> fallBackMethod(Exception ex){
        return new ArrayList<>();
    }

}
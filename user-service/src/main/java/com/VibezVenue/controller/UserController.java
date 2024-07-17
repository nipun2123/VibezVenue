package com.VibezVenue.controller;

import com.VibezVenue.EventServiceProxy;
import com.VibezVenue.config.KafkaProducerConfig;
import com.VibezVenue.dto.EventResponse;
import com.VibezVenue.model.BookedEvent;
import com.VibezVenue.service.BookEventService;
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
    private final KafkaProducerConfig kafkaProducerConfig;
    private final BookEventService bookEventService;


    // User code Event code
    // call event service check availability using event code
    // if av>=1
        //save both booked event
       // do kafka
    // if not
       // say sorry
    @PostMapping("/{usercode}/{eventcode}")
    @CircuitBreaker(name = "user")
    public String bookEvent(@PathVariable("usercode")String userCode, @PathVariable("eventcode")String eventCode){

        int availableTickets =  eventServiceProxy.avilableTickets(eventCode);

        if(availableTickets>=1){
            try {
                return bookEventService.bookEvent(userCode, eventCode);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }else{
            return "Sorry, No space!";
        }

    }

    public List<EventResponse> viewMyEvent(){
        return eventServiceProxy.findAllEvents();
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
package com.VibezVenue.controller;


import com.VibezVenue.config.JsonConfig;
import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import com.VibezVenue.model.BookedEvent;
import com.VibezVenue.service.EventService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/event")
public class EventController {

    @Autowired
    private final EventService eventService;

    @Autowired
    private final ObjectMapper objectMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String saveEvent(@RequestBody EventRequest eventRequest){

        eventService.saveEvent(eventRequest);
        return "Event is saved successfully!";
    }

    @GetMapping("/all")
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

    @GetMapping("/available/{eventcode}")
    @ResponseStatus(HttpStatus.OK)
    public int avilableTickets(@PathVariable("eventcode")String eventCode){

        return eventService.getAvailableTickets(eventCode);
    }


    @KafkaListener(topics = "booking-success", groupId = "notificationId1")
    public void notificationListner(String data){

        Map<String, String> dataLoad = readJsonAsMap(data);



    }





    private Map<String, String> readJsonAsMap(final String json) {
        try{
            final TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
            return objectMapper.readValue(json, typeRef);
        } catch(JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }


}

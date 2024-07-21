package com.VibezVenue.service;

import com.VibezVenue.config.KafkaProducerConfig;
import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import com.VibezVenue.model.BookedEvent;
import com.VibezVenue.model.Event;
import com.VibezVenue.model.Org;
import com.VibezVenue.repository.BookedEventRepository;
import com.VibezVenue.repository.EventRepository;
import com.VibezVenue.repository.OrgRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Autowired
    private final OrgRepository orgRepository;

    @Autowired
    private final EventRepository eventRepository;

    @Autowired
    private final BookedEventRepository bookedEventRepository;

    @Autowired
    private final ObjectMapper objectMapper;

    @Autowired
    private final KafkaProducerConfig kafkaProducerConfig;

    @Transactional
    @Override
    public void saveEvent(EventRequest eventRequest) {

        Org org = orgRepository.getReferenceById(eventRequest.getOrgID());



            Event event = Event.builder()
                    .eventTitle(eventRequest.getEventTitle())
                    .description(eventRequest.getDescription())
                    .capacity(eventRequest.getCapacity())
                    .price(eventRequest.getPrice())
                    .location(eventRequest.getLocation())
                    .startTime(eventRequest.getStartTime())
                    .endTime(eventRequest.getEndTime())
                    .org(org)
                    .build();
        if(eventRequest.getEventCode()==null){
            event.setEventCode(generateEventCode());
        }

            eventRepository.save(event);

            log.info("Event is save. Event id is {}", event.getId());



    }


    @Override
    public List<EventResponse> getAllEvents() {
        List<Event> allEvents = eventRepository.findAll();
        if(!allEvents.isEmpty()) {
            return allEvents.stream().map(this::mapToDto).toList();
        }
        return null;
    }

    @Override
    public EventResponse getEventByCode(String eventCode) {
        if(eventCode != null){
         eventCode = eventCode.trim();

        Optional<Event> eventOptional = eventRepository.findByEventCode(eventCode);
        if (eventOptional.isPresent()){
            Event event = eventOptional.get();
            return EventResponse.builder()
                    .eventCode(event.getEventCode())
                    .eventTitle(event.getEventTitle())
                    .description(event.getDescription())
                    .price(event.getPrice())
                    .location(event.getLocation())
                    .startTime(event.getStartTime())
                    .endTime(event.getEndTime())
                    .capacity(event.getCapacity())
                    .build();
        }
        }
        throw new RuntimeException("Event not found!! The code is "+ eventCode);
    }

    @Override
    public int getAvailableTickets(String eventCode) {
        if(eventCode != null) {
            eventCode = eventCode.trim();

            Optional<Event> eventOptional = eventRepository.findByEventCode(eventCode);
            if (eventOptional.isPresent()) {

               long soldTicketCount = bookedEventRepository.countByEvent(eventOptional.get());

               int capacity = eventOptional.get().getCapacity();

               return (int) (capacity - soldTicketCount);


            }

        }

        return -1;
    }



    @Transactional
    @Override
    public void bookEvent(String data) {

        Map<String, String> dataLoad = readJsonAsMap(data);

        log.info("No error yet!");
        BookedEvent bookedEvent = generateBookedEvent(dataLoad);
        bookedEventRepository.save(bookedEvent);
        log.info("Event Booked!");

        kafkaProducerConfig.kafkaTemplate().send("booking-success", String.format("User id is %s booked event id is %s", bookedEvent.getUserCode(), bookedEvent.getEvent().getEventCode()));

    }

    private BookedEvent generateBookedEvent(Map<String, String> dataLoad){

        BookedEvent bookedEvent = BookedEvent.builder().userCode(dataLoad.get("userCode")).bookedDateTime(LocalDateTime.parse(dataLoad.get("bookedDateTime")))
                .event(eventRepository.findByEventCode(dataLoad.get("eventCode")).get()).build();


        return bookedEvent;

    }


    private Map<String, String> readJsonAsMap(final String json) {
        try{
            final TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String,String>>() {};
            return objectMapper.readValue(json, typeRef);
        } catch(JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }
    }


    private EventResponse mapToDto(Event event) {
        return EventResponse.builder()
                .eventCode(event.getEventCode())
                .eventTitle(event.getEventTitle())
                .description(event.getDescription())
                .price(event.getPrice())
                .location(event.getLocation())
                .startTime(event.getStartTime())
                .endTime(event.getEndTime())
                .capacity(event.getCapacity())
                .build();
    }

    private String generateEventCode() {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddhhmmssMs");
        return simpleDateFormat.format(date);
    }
}

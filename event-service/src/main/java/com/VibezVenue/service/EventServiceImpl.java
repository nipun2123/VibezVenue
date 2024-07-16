package com.VibezVenue.service;

import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import com.VibezVenue.model.Event;
import com.VibezVenue.model.Org;
import com.VibezVenue.repository.BookedEventRepository;
import com.VibezVenue.repository.EventRepository;
import com.VibezVenue.repository.OrgRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;


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
                    .startDate(eventRequest.getStartDate())
                    .endDate(eventRequest.getEndDate())
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
                    .startDate(event.getStartDate())
                    .startTime(event.getStartTime())
                    .endDate(event.getEndDate())
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

    private EventResponse mapToDto(Event event) {
        return EventResponse.builder()
                .eventCode(event.getEventCode())
                .eventTitle(event.getEventTitle())
                .description(event.getDescription())
                .price(event.getPrice())
                .location(event.getLocation())
                .startDate(event.getStartDate())
                .startTime(event.getStartTime())
                .endDate(event.getEndDate())
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

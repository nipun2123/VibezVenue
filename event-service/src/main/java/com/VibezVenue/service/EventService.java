package com.VibezVenue.service;

import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EventService {

    void saveEvent(EventRequest eventRequest);
    List<EventResponse> getAllEvents();
    EventResponse getEventByCode(String eventCode);

    int getAvailableTickets(String eventCode);

    void bookEvent(String data);

}

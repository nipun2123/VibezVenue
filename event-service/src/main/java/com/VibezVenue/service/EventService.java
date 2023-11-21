package com.VibezVenue.service;

import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;

import java.util.List;

public interface EventService {

    void saveEvent(EventRequest eventRequest);
    List<EventResponse> getAllEvents();
    EventResponse getEventById(String eventId);
}

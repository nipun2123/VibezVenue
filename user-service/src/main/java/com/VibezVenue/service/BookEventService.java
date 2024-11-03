package com.VibezVenue.service;

import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface BookEventService {

    String bookEvent(String userCode, String eventCode);
}

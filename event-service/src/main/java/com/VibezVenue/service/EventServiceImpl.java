package com.VibezVenue.service;

import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.model.Event;
import com.VibezVenue.model.Org;
import com.VibezVenue.repository.EventRepository;
import com.VibezVenue.repository.OrgRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final OrgRepository orgRepository;

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
                .eventCode(generateEventCode())
                .org(org)
                .build();

        eventRepository.save(event);

        log.info("Event is save. Event id is {}", event.getId());

    }

    private String generateEventCode() {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddhhmmssMs");
        return simpleDateFormat.format(date);
    }
}

package jUnit;

import com.VibezVenue.dto.EventResponse;
import com.VibezVenue.model.Event;
import com.VibezVenue.repository.BookedEventRepository;
import com.VibezVenue.repository.EventRepository;
import com.VibezVenue.service.EventService;
import com.VibezVenue.service.EventServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(MockitoExtension.class)
class EventServiceImplTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private BookedEventRepository bookedEventRepository;

    @InjectMocks
    private EventServiceImpl eventService;

    @Test
    public void getAllEvents() {
        Event event = Event.builder().eventTitle("TestTitle")
                .eventCode("testevent")
                .description("TestDescription")
                .location("TestLocation")
                .capacity(40)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();
        List<Event> eventResponseList = new ArrayList<>();
        eventResponseList.add(event);


        when(eventRepository.findAll()).thenReturn(eventResponseList);

        int size = eventService.getAllEvents().size();
        assertEquals(size, 1);
    }

    @Test
    public void getEventByCode() {

        Event event = Event.builder().eventTitle("TestTitle")
                .eventCode("testevent")
                .description("TestDescription")
                .location("TestLocation")
                .capacity(40)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();
        when(eventRepository.findByEventCode("testevent")).thenReturn(Optional.of(event));

        EventResponse eventResponse = eventService.getEventByCode("testevent");
        assertEquals("testevent", eventResponse.getEventCode());
    }

    @Test
    public void getAvailableTickets() {
        Event event = Event.builder().eventTitle("TestTitle")
                .eventCode("testevent")
                .description("TestDescription")
                .location("TestLocation")
                .capacity(40)
                .startTime(LocalDateTime.now())
                .endTime(LocalDateTime.now())
                .build();
        when(eventRepository.findByEventCode("testevent")).thenReturn(Optional.of(event));
        when(bookedEventRepository.countByEvent(event)).thenReturn(30L);

        int availableTickets = eventService.getAvailableTickets("testevent");

        assertEquals(10, availableTickets);

    }

}
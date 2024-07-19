package com.VibezVenue.service;

import com.VibezVenue.config.KafkaProducerConfig;
import com.VibezVenue.dto.SuccessBookedEvent;
import com.VibezVenue.model.BookedEvent;
import com.VibezVenue.model.User;
import com.VibezVenue.repository.BookedEventRepository;
import com.VibezVenue.repository.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;


@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class BookEventServiceImpl implements BookEventService {



    @Autowired
    private final BookedEventRepository bookedEventRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final KafkaProducerConfig kafkaProducerConfig;

    @Autowired
    private final ObjectMapper objectMapper;

    //find user by code
    //generate ticket no
    //get today date
    //save BookedEvent
    //Sent details to event service
    @Transactional
    @Override
    public String bookEvent(String userCode, String eventCode) {

        Optional<User> user = userRepository.findByUserCode(userCode);

        if(user.isPresent()){
            BookedEvent bookedEvent = BookedEvent.builder().user(user.get()).eventCode(eventCode).
                    bookedDateTime(LocalDateTime.now()).ticketNo(generateTicketNumber()).build();

            BookedEvent savedEvent = bookedEventRepository.save(bookedEvent);

            //Should send only required data to event Server

            SuccessBookedEvent successBookedEvent = SuccessBookedEvent.builder()
                    .eventCode(savedEvent.getEventCode()).userCode(savedEvent.getUser().getUserCode())
                    .bookedDateTime(savedEvent.getBookedDateTime()).build();

            try {
                kafkaProducerConfig.kafkaTemplate().send("booking-success", objectMapper.writeValueAsString(successBookedEvent));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

        }else{
            return "User not available!";
        }

        log.info("Event Saved Successfully!");
        return "Event Saved Successfully!";
    }

    private String generateTicketNumber() {

        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyMMddhhmmssMs");
        return simpleDateFormat.format(date);
    }
}

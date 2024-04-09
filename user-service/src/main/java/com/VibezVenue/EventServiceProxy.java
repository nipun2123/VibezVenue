package com.VibezVenue;

import com.VibezVenue.dto.EventResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.Optional;

@FeignClient(name = "event-service")
public interface EventServiceProxy {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventResponse> findAllEvents();

    @GetMapping("/{eventcode}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<EventResponse> findEventByCode(@PathVariable("eventcode")String eventCode);
}

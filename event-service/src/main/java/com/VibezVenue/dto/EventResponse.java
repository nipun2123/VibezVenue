package com.VibezVenue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventResponse {

    private String eventCode;
    private String eventTitle;
    private String description;
    private int capacity;
    private BigDecimal price;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
}

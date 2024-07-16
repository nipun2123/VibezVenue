package com.VibezVenue.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EventRequest {

    private String eventCode;
    private String eventTitle;
    private String description;
    private int capacity;
    private BigDecimal price;
    private String location;
    private Date startDate;
    private Date endDate;
    private Time startTime;
    private Time endTime;
    private long orgID;
}

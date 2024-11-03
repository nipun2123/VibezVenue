package com.VibezVenue.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "booked_event")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class BookedEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userCode;
    private LocalDateTime bookedDateTime;
    @ManyToOne()
    @JoinColumn(name = "event_id")
    private Event event;

}

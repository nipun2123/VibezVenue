package com.VibezVenue.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDateTime;

@Entity
@Table(name = "event")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String eventCode;
    private String eventTitle;
    private String description;
    private int capacity;
    private BigDecimal price;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne()
    @JoinColumn(name = "org_id")
    private Org org;

}

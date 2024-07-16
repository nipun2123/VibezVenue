package com.VibezVenue.model;

import jakarta.persistence.*;
import lombok.*;

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
    private String ticketNo;
    private String eventCode;
    private LocalDateTime bookedDateTime;
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

}

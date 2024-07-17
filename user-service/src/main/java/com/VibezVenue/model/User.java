package com.VibezVenue.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "user_management")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String userCode;
    private String fName;
    private String lName;
    private String email;
    private String number;

}

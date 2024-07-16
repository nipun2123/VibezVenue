package com.VibezVenue.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "org")
@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
public class Org {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String orgName;
    private String aboutUs;
    @OneToMany(mappedBy = "org")
    private List<Event> events;

    public Org(String orgName, String aboutUs) {
        this.orgName = orgName;
        this.aboutUs = aboutUs;
    }
}

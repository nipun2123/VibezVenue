package com.VibezVenue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class EventServiceApplication {
    public static void main(String[] args) {

        SpringApplication.run(EventServiceApplication.class, args);
    }

}
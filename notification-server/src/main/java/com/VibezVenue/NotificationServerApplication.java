package com.VibezVenue;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

@SpringBootApplication
@Slf4j
public class NotificationServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(NotificationServerApplication.class, args);
    }

    @KafkaListener(topics = "booking-success", groupId = "notificationId")
    public void notificationListner(String data){

        log.info("Notification received! "+ data);
    }


}

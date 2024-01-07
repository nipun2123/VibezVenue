package com.VibezVenue.controller;

import com.VibezVenue.dto.EventRequest;
import com.VibezVenue.dto.EventResponse;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.Network;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.*;


@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class EventControllerTest {

    private static final Network network = Network.newNetwork();
    private static final String ACCOUNT_SERVICE_DB = "event-service-db";

    private static String eventCode;

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>( DockerImageName.parse("postgres:16.1"))
            .withDatabaseName("event-service")
            .withUsername("postgres")
            .withPassword("admin")
            .withNetwork(network)
            .withNetworkAliases(ACCOUNT_SERVICE_DB)
            .withExposedPorts(5432);

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    TestRestTemplate testRestTemplate;

    @BeforeEach
    public void setup(){

        EventRequest event = EventRequest.builder()
                .eventTitle("Disco Night")
                .description("xxxxxxxxxxxx")
                .capacity(200)
                .price(BigDecimal.valueOf(30))
                .location("Newcastle")
                .orgID(1)
                .build();
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/event", HttpMethod.POST, new HttpEntity<EventRequest>(event), String.class);
        EventResponse[] eventResponses = testRestTemplate.getForObject("/api/event", EventResponse[].class);
        eventCode = eventResponses[0].getEventCode();
    }

    @Test
    public void shouldCreateNewEvent(){

        EventRequest event = EventRequest.builder()
                .eventTitle("Disco Night")
                .description("xxxxxxxxxxxx")
                .capacity(200)
                .price(BigDecimal.valueOf(30))
                .location("Newcastle")
                .orgID(1)
                .build();
        ResponseEntity<String> responseEntity = testRestTemplate.exchange("/api/event", HttpMethod.POST, new HttpEntity<EventRequest>(event), String.class);

        assertThat(responseEntity.getBody()).isEqualTo("Event is saved successfully!");
    }

    @Test
    public void shouldHaveData(){
        EventResponse[] eventResponses = testRestTemplate.getForObject("/api/event", EventResponse[].class);

        assertThat(eventResponses).isNotEmpty();
    }

    @Test
    public void shouldGetDataFromCode(){
        Optional<LinkedHashMap<String,String>> eventResponses = testRestTemplate.getForObject("/api/event/"+eventCode, Optional.class);
        if(eventResponses.isPresent()) {
            LinkedHashMap<String, String> eventResponse = eventResponses.get();
            assertThat(eventResponse.get("eventCode")).isEqualTo(eventCode);
        }else {
            assertThat(true).isEqualTo(false);
        }

    }

}
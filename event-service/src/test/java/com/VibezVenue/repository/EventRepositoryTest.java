package com.VibezVenue.repository;

import com.VibezVenue.model.Event;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.*;

@Testcontainers
@SpringBootTest
@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EventRepositoryTest {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>( DockerImageName.parse("postgres:16.1"))
            .withDatabaseName("event-service")
            .withUsername("postgres")
            .withPassword("admin");


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("event-service", postgreSQLContainer::getDatabaseName);
        registry.add("postgres", postgreSQLContainer::getUsername);
        registry.add("admin", postgreSQLContainer::getPassword);
    }

    static {
        postgreSQLContainer.start();
    }
    @Autowired
    EventRepository eventRepository;
    OrgRepository orgRepository;




    @Test
    public void isConnectionEstablished()  {
        assertThat(postgreSQLContainer.isCreated()).isTrue();
        assertThat(postgreSQLContainer.isRunning()).isTrue();

        List<Event> eventResponses = eventRepository.findAll();

        assertThat(eventResponses.size()).isEqualTo(1);
    }


}
package com.VibezVenue.repository;

import com.VibezVenue.model.BookedEvent;
import com.VibezVenue.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookedEventRepository extends JpaRepository<BookedEvent, Long> {
    Long countByEvent(Event event);
}

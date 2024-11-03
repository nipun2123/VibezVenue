package com.VibezVenue.repository;

import com.VibezVenue.model.BookedEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookedEventRepository extends JpaRepository<BookedEvent, Long> {

}

package com.VibezVenue.repository;

import com.VibezVenue.model.BookedEvent;
import com.VibezVenue.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserCode(String userCode);

}

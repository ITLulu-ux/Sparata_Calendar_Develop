package org.example.sparata_calendar_develop.Repository;

import org.example.sparata_calendar_develop.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByOrderByModifiedAtDesc();

    Optional<User> findByEmail(String email);
}

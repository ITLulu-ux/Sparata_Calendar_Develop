package org.example.sparata_calendar_develop.Repository;

import org.example.sparata_calendar_develop.Entity.Calendar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findAllByOrderByModifiedAtDesc();

    Page<Calendar> findAllByOrderByModifiedAtDesc(Pageable pageable);

    boolean existsByUserId(Long userId);
}

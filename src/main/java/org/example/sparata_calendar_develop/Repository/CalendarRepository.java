package org.example.sparata_calendar_develop.Repository;

import org.example.sparata_calendar_develop.Entity.Calendar;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CalendarRepository extends JpaRepository<Calendar, Long> {

    List<Calendar> findAllByOrderByModifiedAtDesc();

    List<Calendar> findAllByWriterOrderByModifiedAtDesc(String writer);


}

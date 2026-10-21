package org.example.sparata_calendar_develop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SparataCalendarDevelopApplication {

    public static void main(String[] args) {
        SpringApplication.run(SparataCalendarDevelopApplication.class, args);
    }

}

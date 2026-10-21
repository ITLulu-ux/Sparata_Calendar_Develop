package org.example.sparata_calendar_develop.Dto;

import lombok.Getter;
import org.example.sparata_calendar_develop.Entity.User;

import java.time.LocalDateTime;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String username;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
    }
}

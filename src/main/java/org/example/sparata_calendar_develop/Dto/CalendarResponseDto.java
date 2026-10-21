package org.example.sparata_calendar_develop.Dto;

import lombok.Getter;
import org.example.sparata_calendar_develop.Entity.Calendar;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CalendarResponseDto {
    private Long id;
    private String title;
    private String contents;
    private Long userId;
    private String writer;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<CommentResponseDto> comments;


    public CalendarResponseDto(Calendar calendar, List<CommentResponseDto> comments) {
        this.id = calendar.getId();
        this.title = calendar.getTitle();
        this.contents = calendar.getContents();

        if (calendar.getUser() != null) {
            this.userId = calendar.getUser().getId();
            this.writer = calendar.getUser().getUsername();
        }

        this.createdAt = calendar.getCreatedAt();
        this.modifiedAt = calendar.getModifiedAt();
        this.comments = comments;
    }

}


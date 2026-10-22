package org.example.sparata_calendar_develop.Dto;

import lombok.Getter;
import org.example.sparata_calendar_develop.Entity.Comment;

import java.time.LocalDateTime;

@Getter
public class CommentResponseDto {

    private Long id;
    private String contents;
    private String writer;
    private Long userId;
    private Long scheduleId;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.contents = comment.getContents();

        if (comment.getUser() != null) {
            this.writer = comment.getUser().getUsername();
            this.userId = comment.getUser().getId();
        }

        if (comment.getCalendar() != null) {
            this.scheduleId = comment.getCalendar().getId();
        }

        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();
    }
}


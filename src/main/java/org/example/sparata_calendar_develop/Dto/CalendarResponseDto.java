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
    private Long commentCount;


    // 기존 생성자: 상세 조회 및 댓글 목록용
    public CalendarResponseDto(
            Calendar calendar,
            List<CommentResponseDto> comments
    ) {
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

    // 새 생성자: 페이지 조회 및 댓글 개수용
    public CalendarResponseDto(Calendar calendar, long commentCount) {
        this.id = calendar.getId();
        this.title = calendar.getTitle();
        this.contents = calendar.getContents();

        if (calendar.getUser() != null) {
            this.userId = calendar.getUser().getId();
            this.writer = calendar.getUser().getUsername();
        }

        this.createdAt = calendar.getCreatedAt();
        this.modifiedAt = calendar.getModifiedAt();
        this.commentCount = commentCount;
    }

}


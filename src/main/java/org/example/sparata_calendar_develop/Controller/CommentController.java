
package org.example.sparata_calendar_develop.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sparata_calendar_develop.Dto.CommentRequestDto;
import org.example.sparata_calendar_develop.Dto.CommentResponseDto;
import org.example.sparata_calendar_develop.Service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    // 댓글 생성
    @PostMapping("/{scheduleId}/comments")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentResponseDto addComment(
            @PathVariable Long scheduleId,
            @Valid @RequestBody CommentRequestDto requestDto,
            HttpSession session
    ) {
        Long loginUserId = getLoginUserId(session);
        return commentService.create(scheduleId, loginUserId, requestDto);
    }

    // 전체 댓글 조회
    @GetMapping("/comments")
    public List<CommentResponseDto> findAllComments() {
        return commentService.findAll();
    }

    // 댓글 수정
    @PatchMapping("/comments/{commentId}")
    public CommentResponseDto updateComment(
            @PathVariable Long commentId,
            @Valid @RequestBody CommentRequestDto requestDto,
            HttpSession session
    ) {
        Long loginUserId = getLoginUserId(session);
        return commentService.update(commentId, loginUserId, requestDto);
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteComment(
            @PathVariable Long commentId,
            HttpSession session
    ) {
        Long loginUserId = getLoginUserId(session);
        commentService.delete(commentId, loginUserId);
    }

    // 로그인 세션 확인
    private Long getLoginUserId(HttpSession session) {
        Long loginUserId = (Long) session.getAttribute("loginUserId");

        if (loginUserId == null) {
            throw new org.springframework.web.server.ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "로그인이 필요합니다."
            );
        }

        return loginUserId;
    }
}



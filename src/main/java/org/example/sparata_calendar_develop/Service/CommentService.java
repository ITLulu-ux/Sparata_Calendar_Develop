
package org.example.sparata_calendar_develop.Service;

import lombok.RequiredArgsConstructor;
import org.example.sparata_calendar_develop.Dto.CommentRequestDto;
import org.example.sparata_calendar_develop.Dto.CommentResponseDto;
import org.example.sparata_calendar_develop.Entity.Calendar;
import org.example.sparata_calendar_develop.Entity.Comment;
import org.example.sparata_calendar_develop.Entity.User;
import org.example.sparata_calendar_develop.Repository.CalendarRepository;
import org.example.sparata_calendar_develop.Repository.CommentRepository;
import org.example.sparata_calendar_develop.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final CalendarRepository calendarRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Transactional
    public CommentResponseDto create(
            Long scheduleId,
            Long loginUserId,
            CommentRequestDto requestDto
    ) {
        Calendar calendar = calendarRepository.findById(scheduleId)
                .orElseThrow(() ->
                        new IllegalArgumentException("일정을 찾을 수 없습니다."));

        User user = userRepository.findById(loginUserId)
                .orElseThrow(() ->
                        new IllegalArgumentException("유저를 찾을 수 없습니다."));

        long commentCount = commentRepository.countByCalendarId(scheduleId);

        if (commentCount >= 10) {
            throw new IllegalArgumentException(
                    "일정 하나에는 댓글을 최대 10개까지 작성할 수 있습니다."
            );
        }

        Comment comment = new Comment(
                requestDto.getContents(),
                user,
                calendar
        );

        return new CommentResponseDto(commentRepository.save(comment));
    }

    // 전체 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentResponseDto> findAll() {
        return commentRepository.findAll()
                .stream()
                .map(CommentResponseDto::new)
                .toList();
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto update(
            Long commentId,
            Long loginUserId,
            CommentRequestDto requestDto
    ) {
        Comment comment = findComment(commentId);
        checkWriter(comment, loginUserId);

        comment.update(requestDto.getContents());

        return new CommentResponseDto(comment);
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long commentId, Long loginUserId) {
        Comment comment = findComment(commentId);
        checkWriter(comment, loginUserId);

        commentRepository.delete(comment);
    }

    // 댓글 ID로 조회
    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() ->
                        new IllegalArgumentException("댓글을 찾을 수 없습니다."));
    }

    // 작성자 확인
    private void checkWriter(Comment comment, Long loginUserId) {
        if (!comment.getUser().getId().equals(loginUserId)) {
            throw new IllegalStateException(
                    "댓글 작성자만 수정하거나 삭제할 수 있습니다."
            );
        }
    }
}


package org.example.sparata_calendar_develop.Service;

import lombok.RequiredArgsConstructor;
import org.example.sparata_calendar_develop.Dto.CalendarRequestDto;
import org.example.sparata_calendar_develop.Dto.CalendarResponseDto;
import org.example.sparata_calendar_develop.Dto.CommentResponseDto;
import org.example.sparata_calendar_develop.Entity.Calendar;
import org.example.sparata_calendar_develop.Entity.User;
import org.example.sparata_calendar_develop.Repository.CalendarRepository;
import org.example.sparata_calendar_develop.Repository.CommentRepository;
import org.example.sparata_calendar_develop.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {

    private final CalendarRepository calendarRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    // 일정 생성
    @Transactional
    public CalendarResponseDto create(CalendarRequestDto requestDto) {
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() ->
                        new IllegalArgumentException("유저를 찾을 수 없습니다."));

        Calendar calendar = new Calendar(
                requestDto.getTitle(),
                requestDto.getContents(),
                user
        );

        Calendar savedCalendar = calendarRepository.save(calendar);

        return new CalendarResponseDto(savedCalendar, List.of());
    }

    // 일정 페이지 조회
    @Transactional(readOnly = true)
    public Page<CalendarResponseDto> findAll(Pageable pageable) {
        Page<Calendar> calendars =
                calendarRepository.findAllByOrderByModifiedAtDesc(pageable);

        return calendars.map(calendar -> {
            long commentCount =
                    commentRepository.countByCalendarId(calendar.getId());

            return new CalendarResponseDto(calendar, commentCount);
        });
    }

    // 일정 상세 조회
    @Transactional(readOnly = true)
    public CalendarResponseDto findById(Long id) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("일정을 찾을 수 없습니다."));

        List<CommentResponseDto> comments =
                commentRepository.findAllByCalendarId(id).stream()
                        .map(CommentResponseDto::new)
                        .toList();

        return new CalendarResponseDto(calendar, comments);
    }

    // 일정 수정
    @Transactional
    public CalendarResponseDto update(
            Long id,
            CalendarRequestDto requestDto
    ) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("일정을 찾을 수 없습니다."));

        calendar.update(requestDto.getTitle());

        return new CalendarResponseDto(calendar, List.of());
    }

    // 일정 삭제
    @Transactional
    public void delete(Long id) {
        Calendar calendar = calendarRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException("일정을 찾을 수 없습니다."));

        calendarRepository.delete(calendar);
    }
}


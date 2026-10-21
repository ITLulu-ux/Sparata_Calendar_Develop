package org.example.sparata_calendar_develop.Service;

import lombok.RequiredArgsConstructor;
import org.example.sparata_calendar_develop.Dto.UserRequestDto;
import org.example.sparata_calendar_develop.Dto.UserResponseDto;
import org.example.sparata_calendar_develop.Entity.User;
import org.example.sparata_calendar_develop.Repository.CalendarRepository;
import org.example.sparata_calendar_develop.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;

    // 유저 생성
    @Transactional
    public UserResponseDto createUser(UserRequestDto requestDto) {
        User user = new User(
                requestDto.getUsername(),
                requestDto.getEmail(),
                requestDto.getPassword()
        );

        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser);
    }

    // 유저 전체 조회
    @Transactional(readOnly = true)
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::new)
                .toList();
    }

    // 유저 단건 조회
    @Transactional(readOnly = true)
    public UserResponseDto findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 유저를 찾을 수 없습니다. id=" + id
                ));

        return new UserResponseDto(user);
    }

    // 유저 수정
    @Transactional
    public UserResponseDto updateUser(Long id, UserRequestDto requestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 유저를 찾을 수 없습니다. id=" + id
                ));

        user.update(
                requestDto.getUsername(),
                requestDto.getEmail()
        );

        return new UserResponseDto(user);
    }

    // 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        "해당 유저를 찾을 수 없습니다. id=" + id
                ));

        if (calendarRepository.existsByUserId(id)) {
            throw new IllegalStateException(
                    "작성한 일정이 있어 유저를 삭제할 수 없습니다."
            );
        }

        userRepository.delete(user);
    }
}

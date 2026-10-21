package org.example.sparata_calendar_develop.Controller;

import lombok.RequiredArgsConstructor;
import org.example.sparata_calendar_develop.Dto.UserRequestDto;
import org.example.sparata_calendar_develop.Dto.UserResponseDto;
import org.example.sparata_calendar_develop.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 유저 생성
    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(
            @RequestBody UserRequestDto requestDto
    ) {
        UserResponseDto response = userService.createUser(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 유저 전체 조회
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> findAll() {
        return ResponseEntity.ok(userService.findAll());
    }

    // 유저 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findById(
            @PathVariable Long id
    ) {
        return ResponseEntity.ok(userService.findById(id));
    }

    // 유저 수정
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(
            @PathVariable Long id,
            @RequestBody UserRequestDto requestDto
    ) {
        UserResponseDto response = userService.updateUser(id, requestDto);
        return ResponseEntity.ok(response);
    }

    // 유저 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable Long id
    ) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}

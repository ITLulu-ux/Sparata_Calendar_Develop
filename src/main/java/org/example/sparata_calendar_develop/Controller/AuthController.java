package org.example.sparata_calendar_develop.Controller;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.sparata_calendar_develop.Dto.LoginRequestDto;
import org.example.sparata_calendar_develop.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public String login(
            @Valid @RequestBody LoginRequestDto requestDto,
            HttpSession session
    ) {
        userService.login(requestDto, session);
        return "로그인 성공";
    }

    @PostMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "로그아웃 성공";
    }

    @GetMapping("/check")
    public String checkLogin(HttpSession session) {
        Object loginUserId = session.getAttribute("loginUserId");

        if (loginUserId == null) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "로그인이 필요합니다."
            );
        }

        return "로그인 상태입니다.";
    }
}
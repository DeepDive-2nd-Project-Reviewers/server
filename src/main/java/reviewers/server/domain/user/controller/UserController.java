package reviewers.server.domain.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reviewers.server.domain.user.dto.request.LogoutRequestDto;
import reviewers.server.domain.user.dto.request.RefreshAccessTokenRequestDto;
import reviewers.server.domain.user.dto.request.SignInRequestDto;
import reviewers.server.domain.user.dto.request.SignUpRequestDto;
import reviewers.server.domain.user.service.UserService;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입 엔드포인트
    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody SignUpRequestDto requestDto) {
        return userService.signUp(requestDto);
    }

    // 로그인 엔드포인트
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody SignInRequestDto requestDto) {
        return userService.signIn(requestDto);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestBody RefreshAccessTokenRequestDto requestDto) { return userService.regenAccessToken(requestDto); }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody LogoutRequestDto requestDto) { return userService.logout(requestDto); }
}
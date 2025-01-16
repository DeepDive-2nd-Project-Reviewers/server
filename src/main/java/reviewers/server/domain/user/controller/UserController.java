package reviewers.server.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reviewers.server.domain.user.dto.request.*;
import reviewers.server.domain.user.dto.response.LogoutResponseDto;
import reviewers.server.domain.user.dto.response.RefreshAccessTokenResponseDto;
import reviewers.server.domain.user.dto.response.SignInResponseDto;
import reviewers.server.domain.user.dto.response.SignUpResponseDto;
import reviewers.server.domain.user.service.EmailService;
import reviewers.server.domain.user.service.UserService;
import reviewers.server.global.success.SuccessResponse;

import javax.naming.AuthenticationException;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final EmailService emailService;

    // 회원가입 엔드포인트
    @PostMapping("/sign-up")
    public SuccessResponse<SignUpResponseDto> signUp(@RequestBody SignUpRequestDto requestDto) throws AuthenticationException {
        SignUpResponseDto response = userService.signUp(requestDto);
        return new SuccessResponse<>(response);
    }

    // 로그인 엔드포인트
    @PostMapping("/sign-in")
    public SuccessResponse<SignInResponseDto> signIn(@RequestBody SignInRequestDto requestDto) {
        SignInResponseDto response = userService.signIn(requestDto);
        return new SuccessResponse<>(response);
    }

    // AccessToken 재발급 엔드포인트
    @PostMapping("/refreshToken")
    public SuccessResponse<RefreshAccessTokenResponseDto> refreshToken(@RequestBody RefreshAccessTokenRequestDto requestDto) throws AuthenticationException {
        RefreshAccessTokenResponseDto response = userService.regenAccessToken(requestDto);
        return new SuccessResponse<>(response);
    }

    // 로그아웃 엔드포인트
    @PostMapping("/logout")
    public SuccessResponse<LogoutResponseDto> logout(@RequestBody LogoutRequestDto requestDto) {
        LogoutResponseDto response = userService.logout(requestDto);
        return new SuccessResponse<>(response);
    }

    // 이메일 인증번호 발송 엔드포인트
    @PostMapping("/send")
    public SuccessResponse sendCertificationEmail(@RequestBody @Valid EmailCertificationRequestDto requestDto) {
            emailService.sendCertificationEmail(requestDto);
            return SuccessResponse.ok();
    }

    // 이메일 인증번호 검증 엔드포인트
    @PostMapping("/verify")
    public SuccessResponse verifyCertification(@RequestBody @Valid CheckCertificationRequestDto requestDto) throws AuthenticationException {
        emailService.checkCertificationNumber(requestDto);
        return SuccessResponse.ok();
    }
}

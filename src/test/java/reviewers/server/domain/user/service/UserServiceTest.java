package reviewers.server.domain.user.service;

import com.sun.jdi.LongValue;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import reviewers.server.domain.user.dto.request.*;
import reviewers.server.domain.user.dto.response.LogoutResponseDto;
import reviewers.server.domain.user.dto.response.RefreshAccessTokenResponseDto;
import reviewers.server.domain.user.dto.response.SignInResponseDto;
import reviewers.server.domain.user.dto.response.SignUpResponseDto;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.provider.JwtProvider;
import reviewers.server.domain.user.repository.UserRepository;

import java.time.LocalDate;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    // 테스트 대상 클래스는 Mock 객체가 아닌, 의존성을 주입받은 실제 객체
    @InjectMocks
    private UserService userService;

    @Mock
    private EmailService emailService;

    @Mock
    private RedisService redisService;

    @Mock
    private JwtProvider jwtProvider;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Test
    public void signUp() {
        // Given
        SignUpRequestDto requestDto = new SignUpRequestDto(
                "test@email.com",
                "password",
                "testUser",
                LocalDate.of(2020,1,1),
                "000000"
        );

        when(userRepository.existsByEmail(requestDto.getEmail())).thenReturn(false);
        when(userRepository.existsByUsername(requestDto.getUsername())).thenReturn(false);
        when(emailService.checkCertificationNumber(any(CheckCertificationRequestDto.class))).thenReturn(true);

        // When
        SignUpResponseDto responseDto = userService.signUp(requestDto);

        // Then
        Assertions.assertThat(responseDto.getMessage()).isEqualTo("가입 성공");
    }

    @Test
    public void signIn() {
        // Given
        SignInRequestDto requestDto = new SignInRequestDto(
                "test@email.com",
                "password"
        );

        String encodedPassword = new BCryptPasswordEncoder().encode(requestDto.getPassword());

        User user = new User(
                "test@email.com",
                encodedPassword,
                "testUser",
                LocalDate.of(2020,1,1)
        );

        when(userRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.of(user));
        when(jwtProvider.createAccessToken(user.getUserId(), user.getEmail(), String.valueOf(user.getRole()))).thenReturn("AccessToken");
        when(jwtProvider.createRefreshToken(user.getUserId())).thenReturn("RefreshToken");

        // When
        SignInResponseDto responseDto = userService.signIn(requestDto);

        // Then
        Assertions.assertThat(responseDto.getAccessToken()).isEqualTo("AccessToken");
        Assertions.assertThat(responseDto.getRefreshToken()).isEqualTo("RefreshToken");
    }

    @Test
    public void regenAccessToken() {

        // Given
        RefreshAccessTokenRequestDto requestDto = new RefreshAccessTokenRequestDto("RefreshToken");

        User user = new User(
                "test@email.com",
                "password",
                "testUser",
                LocalDate.of(2020,1,1)
        );

        when(redisService.getToken(String.valueOf(0L))).thenReturn("RefreshToken");
        when(userRepository.findByUserId(0L)).thenReturn(Optional.of(user));
        when(jwtProvider.createAccessToken(user.getUserId(), user.getEmail(), String.valueOf(user.getRole()))).thenReturn("accessToken");

        // When
        RefreshAccessTokenResponseDto responseDto = userService.regenAccessToken(requestDto);

        // Then
        Assertions.assertThat(responseDto.getAccessToken()).isEqualTo("accessToken");
    }

    @Test
    void logout() {
        // Given
        Long userId = 1L;

        LogoutRequestDto requestDto = new LogoutRequestDto(
                "accessToken",
                "refreshToken"
        );

        doNothing().when(redisService).blackListAccessToken("accessToken");
        when(jwtProvider.getUserIdFromRefreshToken("refreshToken")).thenReturn(userId);
        doNothing().when(redisService).deleteRefreshToken(String.valueOf(userId));

        // When
        LogoutResponseDto responseDto = userService.logout(requestDto);

        // Then
        Assertions.assertThat(responseDto.getMessage()).isEqualTo("로그아웃 성공");

    }
}
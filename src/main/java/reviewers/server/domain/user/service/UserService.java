package reviewers.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reviewers.server.domain.user.dto.request.*;
import reviewers.server.domain.user.dto.response.LogoutResponseDto;
import reviewers.server.domain.user.dto.response.RefreshAccessTokenResponseDto;
import reviewers.server.domain.user.dto.response.SignInResponseDto;
import reviewers.server.domain.user.dto.response.SignUpResponseDto;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.provider.JwtProvider;
import reviewers.server.domain.user.repository.UserRepository;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;
    private final RedisService redisService;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public SignUpResponseDto signUp(SignUpRequestDto requestDto) {

        // 이메일 중복 검증
        String email = requestDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new BaseErrorException(ErrorType._DUPLICATED_EMAIL);
        }

        // 이메일 인증 검증
        if (!emailService.checkCertificationNumber(new CheckCertificationRequestDto(requestDto.getEmail(), requestDto.getCertificationNumber()))) {
            throw new BaseErrorException(ErrorType._INVALID_CERTIFICATION_CODE);
        }

        String password = requestDto.getPassword();
        String encodedPassword = passwordEncoder.encode(password);

        //닉네임 중복 검증
        String username = requestDto.getUsername();
        if(userRepository.existsByUsername(username)) {
            throw new BaseErrorException(ErrorType._DUPLICATED_NICKNAME);
        }

        LocalDate birth = requestDto.getBirth();

        String role = requestDto.getRole();

        User user = new User(email, encodedPassword, username, birth, role);
        userRepository.save(user);

        return new SignUpResponseDto(user.getUserId(), "가입 성공");
    }

    public SignInResponseDto signIn(SignInRequestDto requestDto){

        User user = userRepository.findByEmail(requestDto.getEmail())
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_USER));

        String password = requestDto.getPassword();
        String encodedPassword = user.getPassword();
        boolean isMatched = passwordEncoder.matches(password, encodedPassword);
        if(!isMatched){
            throw new BaseErrorException(ErrorType._INVALID_PASSWORD);
        }

        // JWT 토큰 생성
        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());
        int expiredTime = 3600; // 1시간

        redisService.setToken(String.valueOf(user.getUserId()), refreshToken);

        // DTO 반환
        return new SignInResponseDto(accessToken, refreshToken, expiredTime, user.getUserId());
    }

    public RefreshAccessTokenResponseDto regenAccessToken(RefreshAccessTokenRequestDto requestDto) {

        String refreshToken = requestDto.getRefreshToken();

        Long userId = jwtProvider.getUserIdFromRefreshToken(refreshToken);
        boolean isMatched = refreshToken.equals(redisService.getToken(String.valueOf(userId)));
        if(!isMatched) throw new BaseErrorException(ErrorType._INVALID_TOKEN);


        User user = userRepository.findByUserId(userId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_USER));

        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getRole());
        int expiredTime = 3600;

        return new RefreshAccessTokenResponseDto(accessToken, expiredTime);

    }


    public LogoutResponseDto logout(LogoutRequestDto requestDto) {

        redisService.blackListAccessToken(requestDto.getAccessToken());

        Long userId = jwtProvider.getUserIdFromRefreshToken(requestDto.getRefreshToken());
        redisService.deleteRefreshToken(String.valueOf(userId));

        return new LogoutResponseDto("로그아웃 성공");
    }


}

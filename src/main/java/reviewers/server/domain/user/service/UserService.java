package reviewers.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import reviewers.server.domain.user.dto.request.LogoutRequestDto;
import reviewers.server.domain.user.dto.request.RefreshAccessTokenRequestDto;
import reviewers.server.domain.user.dto.request.SignInRequestDto;
import reviewers.server.domain.user.dto.request.SignUpRequestDto;
import reviewers.server.domain.user.dto.response.RefreshAccessTokenResponseDto;
import reviewers.server.domain.user.dto.response.SignInResponseDto;
import reviewers.server.domain.user.dto.response.SignUpResponseDto;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.provider.JwtProvider;
import reviewers.server.domain.user.repository.UserRepository;
import reviewers.server.global.success.SuccessResponse;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> signUp(SignUpRequestDto requestDto){
        try{

            String email = requestDto.getEmail();
            boolean isExistEmail = userRepository.existsByEmail(email);
            if(isExistEmail) return ResponseEntity.badRequest().body(new SignUpResponseDto(null, "중복된 이메일입니다."));

            String password = requestDto.getPassword();
            String encodedPassword = passwordEncoder.encode(password);

            String username = requestDto.getUsername();
            boolean isExistUsername = userRepository.existsByUsername(username);
            if(isExistUsername) return ResponseEntity.badRequest().body(new SignUpResponseDto(null, "중복된 닉네임입니다."));

            LocalDate birth = requestDto.getBirth();

            String role = requestDto.getRole();

            User user = new User(email, encodedPassword, username, birth, role);
            userRepository.save(user);

            return ResponseEntity.ok(new SignUpResponseDto(user.getUserId(), "가입 성공"));

        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(new SignUpResponseDto(null, "서버 오류 발생"));
        }
    }

    public ResponseEntity<?> signIn(SignInRequestDto requestDto){

        User user = userRepository.findByEmail(requestDto.getEmail());
        if(user == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body("가입된 이메일이 아닙니다.");

        String password = requestDto.getPassword();
        String encodedPassword = user.getPassword();
        boolean isMatched = passwordEncoder.matches(password, encodedPassword);
        if(!isMatched) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("비밀 번호가 다릅니다.");

        // JWT 토큰 생성
        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getRole());
        String refreshToken = jwtProvider.createRefreshToken(user.getUserId());
        int expiredTime = 3600; // 1시간

        // DTO 반환
        return ResponseEntity.ok(new SignInResponseDto(accessToken, refreshToken, expiredTime, user.getUserId()));
    }

    public ResponseEntity<?> regenAccessToken(RefreshAccessTokenRequestDto requestDto){
        String refreshToken = requestDto.getRefreshToken();
        Long userId = jwtProvider.getUserIdFromRefreshToken(refreshToken);

        User user = userRepository.findByUserId(userId);

        String accessToken = jwtProvider.createAccessToken(user.getEmail(), user.getRole());
        int expiredTime = 3600;

        return ResponseEntity.ok(new RefreshAccessTokenResponseDto(accessToken, expiredTime));
    }


    public ResponseEntity<?> logout(LogoutRequestDto requestDto) {

        // 로그아웃시 Token 폐기 어떻게 할지 정해야함
        return ResponseEntity.ok(SuccessResponse.ok("로그아웃 성공"));

    }


}

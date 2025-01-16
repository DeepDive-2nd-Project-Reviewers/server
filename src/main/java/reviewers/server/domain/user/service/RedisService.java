package reviewers.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;

    // K : 이메일, V : 인증 코드
    public void setCode(String email, String authCode){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // 유효 시간은 300, 단위는 초로 설정
        valueOperations.set(email, authCode, 300, TimeUnit.SECONDS);
    }

    // K : 이메일, V : RefreshToken
    public void setToken(String email, String refreshToken){
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        // 유효 시간은 7일, 단위는 초로 설정
        valueOperations.set(email, refreshToken, 604800, TimeUnit.SECONDS);
    }

    // K(email)의 V(인증 코드)를 반환
    public String getCode(String email) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String authCode = valueOperations.get(email);
        if(authCode == null)
            throw new BaseErrorException(ErrorType._EXPIRED_CERTIFICATION_CODE);
        return authCode;
    }

    // K(email)의 V(RefreshToken)를 반환
    public String getToken(String userId) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String token = valueOperations.get(userId);
        if(token == null)
            throw new BaseErrorException(ErrorType._EXPIRED_TOKEN);
        return token;
    }

    public void deleteRefreshToken(String userId) {
        if (Boolean.FALSE.equals(redisTemplate.hasKey(userId))) {
            throw new BaseErrorException(ErrorType._NOT_FOUND_TOKEN);
        }
        redisTemplate.delete(userId);
    }

    public void blackListAccessToken(String accessToken){
        // 이미 블랙리스트에 존재하는지 확인
        if (Boolean.TRUE.equals(redisTemplate.hasKey(accessToken))) {
            throw new BaseErrorException(ErrorType._DUPLICATED_TOKEN);
        }

        // 블랙리스트에 추가 (AccessToken이 폐기되기 전까지 유지)
        redisTemplate.opsForValue().set(accessToken, "BLACKLIST", 3600000, TimeUnit.MILLISECONDS);
    }
}

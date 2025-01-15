package reviewers.server.domain.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reviewers.server.domain.user.dto.request.CheckCertificationRequestDto;
import reviewers.server.domain.user.dto.request.EmailCertificationRequestDto;
import reviewers.server.domain.user.provider.EmailProvider;
import reviewers.server.domain.user.repository.UserRepository;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import javax.naming.AuthenticationException;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final UserRepository userRepository;
    private final RedisService redisService;
    private final EmailProvider emailProvider;


    public static String generateCertificationNumber() {
        return String.valueOf(new Random().nextInt(900000) + 100000);
    }

    public void sendCertificationEmail(EmailCertificationRequestDto requestDto) {

        String email = requestDto.getEmail();
        if (userRepository.existsByEmail(email)) {
            throw new BaseErrorException(ErrorType._DUPLICATED_EMAIL);
        }

        String certificationNumber = generateCertificationNumber();
        if(!emailProvider.sendCertificationMail(email, certificationNumber)) {
            throw new BaseErrorException(ErrorType._EMAIL_SEND_FAILED);
        }

        redisService.setCode(email, certificationNumber);

    }


    public boolean checkCertificationNumber(CheckCertificationRequestDto requestDto) {

        String email = requestDto.getEmail();
        String certificationNumber = requestDto.getCertificationNumber();

        String savedCode = redisService.getCode(email);
        if(savedCode == null) throw new BaseErrorException(ErrorType._EXPIRED_CERTIFICATION_CODE);

        if(!certificationNumber.equals(savedCode)) throw new BaseErrorException(ErrorType._INVALID_CERTIFICATION_CODE);

        return true;

    }

}

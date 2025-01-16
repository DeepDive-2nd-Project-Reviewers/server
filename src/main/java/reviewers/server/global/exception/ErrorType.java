package reviewers.server.global.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@Getter
@ToString
public enum ErrorType {

    /* ------------------------------- Content -------------------------------------*/
    _NOT_FOUND_CONTENT(BAD_REQUEST, "C4001", "해당 id에 해당하는 Content를 찾을 수 없습니다."),
    _NOT_FOUND_CATEGORY(BAD_REQUEST, "C4002", "유효하지 않은 Category 입니다."),

    /* ------------------------------- User -------------------------------------*/
    // 400 BAD_REQUEST
    _DUPLICATED_EMAIL(BAD_REQUEST, "C4002","이미 가입된 이메일 입니다."),
    _EMAIL_SEND_FAILED(BAD_REQUEST, "C4003", "이메일 전송이 실패 하였습니다."),
    _EXPIRED_CERTIFICATION_CODE(BAD_REQUEST, "C4004", "만료된 인증 코드입니다."),
    _DUPLICATED_NICKNAME(BAD_REQUEST, "C4005", "이미 사용중인 닉네임 입니다."),
    _NOT_FOUND_USER(BAD_REQUEST, "C4006", "email 해당하는 User를 찾을 수 없습니다."),
    _EXPIRED_TOKEN(BAD_REQUEST, "C4007", "만료된 토큰 입니다."),
    _NOT_FOUND_TOKEN(BAD_REQUEST, "C4008", "만료되거나 삭제된 토큰 입니다."),
    _DUPLICATED_TOKEN(BAD_REQUEST, "C4009", "이미 등록 된 토큰 입니다."),

    // 401 UNAUTHORIZED
    _INVALID_CERTIFICATION_CODE(UNAUTHORIZED, "C4011", "유효하지 않은 인증 코드입니다."),
    _INVALID_PASSWORD(UNAUTHORIZED,"C4012","비밀번호가 일치하지 않습니다."),
    _INVALID_TOKEN(UNAUTHORIZED,"C4013","잘못된 토큰 입니다."),
    _TOKEN_MISSING(UNAUTHORIZED,"C4014", "토큰 값이 다르거나, 입력되지 않았습니다."),

    /* ------------------------------- Review -------------------------------------*/
    _NOT_FOUND_REVIEW(BAD_REQUEST, "R4001", "해당 id에 해당하는 Review를 찾을 수 없습니다."),

    /* ------------------------------- Comment -------------------------------------*/
    _NOT_FOUND_COMMENT(BAD_REQUEST, "Cm4001", "해당 id에 해당하는 Comment를 찾을 수 없습니다."),
    _EMPTY_COMMENT(BAD_REQUEST, "Cm4002", "해당 Review id에 해당하는 Comment를 찾을 수 없습니다."),

    /* ------------------------------- Heart -------------------------------------*/
    _ALREADY_LIKE(BAD_REQUEST, "H4001", "이미 좋아요를 누른 컨텐츠입니다."),
    _NOT_LIKE(BAD_REQUEST, "H4002", "좋아요를 누르지 않은 컨텐츠입니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ErrorType(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

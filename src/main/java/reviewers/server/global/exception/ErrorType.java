package reviewers.server.global.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

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
    _DUPLICATED_EMAIL(BAD_REQUEST, "U4002","이미 가입된 이메일 입니다."),
    _EMAIL_SEND_FAILED(BAD_REQUEST, "U4003", "이메일 전송이 실패 하였습니다."),
    _EXPIRED_CERTIFICATION_CODE(BAD_REQUEST, "U4004", "만료된 인증 코드입니다."),
    _DUPLICATED_NICKNAME(BAD_REQUEST, "U4005", "이미 사용중인 닉네임 입니다."),
    _NOT_FOUND_USER(BAD_REQUEST, "U4006", "email 해당하는 User를 찾을 수 없습니다."),
    _EXPIRED_TOKEN(BAD_REQUEST, "U4007", "만료된 토큰 입니다."),
    _NOT_FOUND_TOKEN(BAD_REQUEST, "U4008", "만료되거나 삭제된 토큰 입니다."),
    _DUPLICATED_TOKEN(BAD_REQUEST, "U4009", "이미 등록 된 토큰 입니다."),

    // 401 UNAUTHORIZED
    _INVALID_CERTIFICATION_CODE(UNAUTHORIZED, "U4011", "유효하지 않은 인증 코드입니다."),
    _INVALID_PASSWORD(UNAUTHORIZED,"U4012","비밀번호가 일치하지 않습니다."),
    _INVALID_TOKEN(UNAUTHORIZED,"U4013","잘못된 토큰 입니다."),
    _INVALID_AUTHENTICATION(UNAUTHORIZED, "U4014", "인증 방식이 유효하지 않습니다."),
    _TOKEN_MISSING(UNAUTHORIZED,"U4015", "토큰 값이 다르거나, 입력되지 않았습니다."),

    _UNAUTHORIZED_USER(BAD_REQUEST, "U4016", "데이터를 수정할 권한이 없습니다."),

    /* ------------------------------- Review -------------------------------------*/
    _NOT_FOUND_REVIEW(BAD_REQUEST, "R4001", "해당 id에 해당하는 Review를 찾을 수 없습니다."),

    /* ------------------------------- Comment -------------------------------------*/
    _NOT_FOUND_COMMENT(BAD_REQUEST, "Cm4001", "해당 id에 해당하는 Comment를 찾을 수 없습니다."),
    _EMPTY_COMMENT(BAD_REQUEST, "Cm4002", "해당 Review id에 해당하는 Comment를 찾을 수 없습니다."),

    /* ------------------------------- Heart -------------------------------------*/
    _ALREADY_LIKE(BAD_REQUEST, "H4001", "이미 좋아요를 했습니다."),
    _NOT_LIKE(BAD_REQUEST, "H4002", "좋아요를 누르지 않아 취소할 좋아요가 없습니다."),

    /* ------------------------------- IMAGE -------------------------------------*/
    _NULL_IMAGE(BAD_REQUEST, "I4001", "이미지가 존재하지 않습니다."),
    _NO_EXTENSION(BAD_REQUEST, "I4002", "확장자가 누락되었습니다."),
    _INVALID_FILE_EXTENSION(BAD_REQUEST, "I4003", "지원하지 않는 확장자 입니다."),
    _IO_EXCEPTION_ON_UPLOAD(BAD_REQUEST, "I4004", "파일을 가져오는데 실패하였습니다."),
    _FAIL_DELETE_IMAGE(BAD_REQUEST, "I4005", "파일을 삭제하는 데 실패하였습니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ErrorType(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

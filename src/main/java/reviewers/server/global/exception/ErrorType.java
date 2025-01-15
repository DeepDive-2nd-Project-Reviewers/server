package reviewers.server.global.exception;

import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@Getter
@ToString
public enum ErrorType {

    /* ------------------------------- Content -------------------------------------*/
    _NOT_FOUND_CONTENT(BAD_REQUEST, "C4001", "해당 id에 해당하는 Content를 찾을 수 없습니다."),

    /* ------------------------------- User -------------------------------------*/
    _NOT_FOUND_USER(BAD_REQUEST, "U4001", "해당 id에 해당하는 User를 찾을 수 없습니다."),

    /* ------------------------------- Review -------------------------------------*/
    _NOT_FOUND_REVIEW(BAD_REQUEST, "R4001", "해당 id에 해당하는 Review를 찾을 수 없습니다."),

    /* ------------------------------- Review -------------------------------------*/
    _NOT_FOUND_COMMENT(BAD_REQUEST, "C4001", "해당 id에 해당하는 Comment를 찾을 수 없습니다.");

    private HttpStatus status;
    private String code;
    private String message;

    ErrorType(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
}

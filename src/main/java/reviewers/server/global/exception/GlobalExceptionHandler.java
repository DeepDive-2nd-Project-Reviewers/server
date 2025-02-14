package reviewers.server.global.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class : {}, Code : {}, Message : {}";
    private static final int INTERNAL_SERVER_ERROR = 500;

    // 사용자 정의 예외 처리
    @ExceptionHandler(BaseErrorException.class)
    public ResponseEntity<ExceptionResponse<Void>> handle(BaseErrorException e) {

        logWarning(e, e.getErrorType().getCode());
        ExceptionResponse<Void> response = ExceptionResponse.fail(e.getErrorType().getCode(), e.getMessage());

        return ResponseEntity
                .status(e.getErrorType().getStatus())
                .body(response);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponse<Void>> handle(Exception e) {

        logWarning(e, "INTERNAL_SERVER_ERROR가 발생했습니다. 관리자에게 문의하세요");
        ExceptionResponse<Void> response = ExceptionResponse.fail("INTERNAL_SERVER_ERROR가 발생했습니다. 관리자에게 문의하세요.", e.getMessage());

        return ResponseEntity
                .status(INTERNAL_SERVER_ERROR)
                .body(response);
    }


    // log.warn이 중복되어 리팩토링
    private void logWarning(Exception e, String errorCode) {
        log.warn(e.getMessage(), e);  // 전체 로그 출력, 운영 단계에서는 삭제
        log.warn(LOG_FORMAT, e.getClass().getSimpleName(), errorCode, e.getMessage());
    }
}

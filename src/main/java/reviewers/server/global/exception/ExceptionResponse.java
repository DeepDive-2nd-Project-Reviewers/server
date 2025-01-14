package reviewers.server.global.exception;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExceptionResponse<T> {

    private String code;
    private String message;

    private ExceptionResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static <T> ExceptionResponse<T> fail(String code, String message) {
        return new ExceptionResponse<>(code, message);
    }
}

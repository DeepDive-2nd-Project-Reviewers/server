package reviewers.server.global.exception;

import lombok.Getter;

@Getter
public class BaseErrorException extends RuntimeException {

    private final ErrorType errorType;

    public BaseErrorException(ErrorType errorType) {
        super(errorType.getMessage());
        this.errorType = errorType;
    }
}

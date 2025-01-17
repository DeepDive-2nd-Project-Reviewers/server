package reviewers.server.global.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import reviewers.server.domain.user.entity.User;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

@Slf4j
public class SecurityUtil {

    private SecurityUtil() {
        throw new UnsupportedOperationException("Utility class cannot be instantiated");
    }

    public static User currentMember(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("authentication: " + authentication.getName());

        if(authentication.getPrincipal() == null || !authentication.isAuthenticated()){
            throw new BaseErrorException(ErrorType._INVALID_AUTHENTICATION);
        }

        Object principal = authentication.getPrincipal();

        return (User) principal;
    }
}

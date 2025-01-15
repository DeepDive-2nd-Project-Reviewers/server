package reviewers.server.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SignInResponseDto {

    private String accessToken;
    private String refreshToken;
    private int expiredTime;
    private Long userId;

}

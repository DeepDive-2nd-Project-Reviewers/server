package reviewers.server.domain.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshAccessTokenResponseDto {

    private String accessToken;
    private int expiredTime;
}

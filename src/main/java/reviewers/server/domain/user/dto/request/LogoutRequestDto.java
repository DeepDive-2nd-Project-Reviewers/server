package reviewers.server.domain.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LogoutRequestDto {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;
}
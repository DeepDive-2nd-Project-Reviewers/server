package reviewers.server.domain.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CheckCertificationRequestDto {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    private String certificationNumber;
}

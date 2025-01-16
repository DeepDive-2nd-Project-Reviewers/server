package reviewers.server.domain.user.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SignUpRequestDto {

    @NotBlank(message = "필수 항목을 확인 하세요.")
    @Email
    private String email;

    @NotBlank(message = "필수 항목을 확인 하세요.")
    @Pattern(
            regexp = "^(?=.[A-Za-z])(?=\\d)[A-Za-z\\d]{8,}$",
            message = "비밀번호는 최소 8자 이상이며, 영문과 숫자를 포함해야 합니다   ."
    )
    private String password;

    @NotBlank(message = "필수 항목을 확인 하세요.")
    @Size(min = 4, max = 20, message = "닉네임은 4~20자 사이 입니다.")
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]$",
            message = "닉네임은 한글, 영문, 숫자만 사용할 수 있습니다."
    )
    private String username;

    @NotNull(message = "필수 항목을 확인 하세요.")
    private LocalDate birth;

    @NotNull(message = "필수 항목을 확인 하세요.")
    private String role;

    @NotBlank
    private String certificationNumber;

}

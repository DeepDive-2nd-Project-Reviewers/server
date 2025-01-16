package reviewers.server.domain.heart.content.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ContentHeartRequestDto {

    @NotBlank
    private Long userId;

    @NotBlank
    private Long contentId;
}

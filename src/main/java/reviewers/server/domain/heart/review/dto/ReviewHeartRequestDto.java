package reviewers.server.domain.heart.review.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReviewHeartRequestDto {

    @NotBlank
    private Long userId;
}

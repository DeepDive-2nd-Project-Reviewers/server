package reviewers.server.domain.review.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReviewRequestDto {

    @NotNull
    private String title;

    @NotNull
    private String content;
    private int starCount;

}

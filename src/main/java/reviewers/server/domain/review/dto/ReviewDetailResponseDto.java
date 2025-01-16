package reviewers.server.domain.review.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import reviewers.server.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ReviewDetailResponseDto {

    private Long id;
    private String username;
    private String title;
    private String content;
    private String heartCount;

    private LocalDateTime lastModified;

    public ReviewDetailResponseDto(Review review) {
        this.id = review.getId();
        this.username = review.getUser().getUsername();
        this.title = review.getTitle();
        this.content = review.getContent();
        this.heartCount = String.valueOf(review.getHeartCount());
    }
}

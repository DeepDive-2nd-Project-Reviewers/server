package reviewers.server.domain.review.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.review.dto.ReviewRequestDto;
import reviewers.server.domain.user.entity.User;
import reviewers.server.global.common.BaseEntity;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private Long starCount;
    private long heartCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", nullable = false)
    private Contents contents;

    @Builder
    public Review(ReviewRequestDto reviewRequestDto, Long count, Contents contents) {
        this.title = reviewRequestDto.getTitle();
        this.content = reviewRequestDto.getContent();
        this.contents = contents;
        this.starCount = count;
    }

    public void updateReview(ReviewRequestDto reviewRequestDto, Long count) {
        this.title = reviewRequestDto.getTitle();
        this.content = reviewRequestDto.getContent();
        this.starCount = count;
    }

    public void addHeartCount() {
        this.heartCount++;
    }

    public void subtractHeartCount() {
        this.heartCount--;
    }
}

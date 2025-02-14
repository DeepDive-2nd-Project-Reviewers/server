package reviewers.server.domain.review.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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

    @Column
    @Min(0)
    @Max(10)
    private int starCount;

    private long heartCount;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contents_id", nullable = false)
    private Contents contents;

    @Builder
    public Review(ReviewRequestDto reviewRequestDto, int count, Contents contents, User user) {
        this.title = reviewRequestDto.getTitle();
        this.content = reviewRequestDto.getContent();
        this.contents = contents;
        this.user = user;
        this.starCount = count;
    }

    public void updateReview(ReviewRequestDto reviewRequestDto, int count) {
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

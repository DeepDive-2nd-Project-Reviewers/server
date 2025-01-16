package reviewers.server.domain.heart.review.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import lombok.NoArgsConstructor;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.user.entity.User;

@Entity
@NoArgsConstructor
public class ReviewHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    private Review review;

    @Builder
    public ReviewHeart(User user, Review review) {
        this.user = user;
        this.review = review;
    }
}

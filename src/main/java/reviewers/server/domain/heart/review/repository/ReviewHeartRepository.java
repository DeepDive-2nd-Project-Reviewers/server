package reviewers.server.domain.heart.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.heart.review.entity.ReviewHeart;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.user.entity.User;

public interface ReviewHeartRepository extends JpaRepository<ReviewHeart, Long> {
    boolean existsByUserAndReview(User user, Review review);

    void deleteByUserAndReview(User user, Review review);
}

package reviewers.server.domain.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.review.entity.Review;

public interface ReviewInterface extends JpaRepository<Review, Long> {
}

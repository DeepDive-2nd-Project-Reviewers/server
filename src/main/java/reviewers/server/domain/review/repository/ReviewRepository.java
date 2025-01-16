package reviewers.server.domain.review.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.review.entity.Review;

import java.util.List;


public interface ReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findAllByContents(Contents content, Pageable pageable);
}

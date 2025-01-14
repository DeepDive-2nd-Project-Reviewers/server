package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.contents.entity.ContentHeart;

public interface ContentHeartRepository extends JpaRepository<ContentHeart, Long> {
}

package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewers.server.domain.contents.entity.ContentHeart;

@Repository
public interface ContentHeartRepository extends JpaRepository<ContentHeart, Long> {
}

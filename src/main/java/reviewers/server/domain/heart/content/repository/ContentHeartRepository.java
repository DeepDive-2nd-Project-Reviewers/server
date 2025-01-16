package reviewers.server.domain.heart.content.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.heart.content.entity.ContentHeart;
import reviewers.server.domain.user.entity.User;

public interface ContentHeartRepository extends JpaRepository<ContentHeart, Long> {
    boolean existsByUserAndContent(User user, Contents content);

    void deleteByUserAndContent(User user, Contents content);
}

package reviewers.server.domain.comment.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.comment.entity.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}

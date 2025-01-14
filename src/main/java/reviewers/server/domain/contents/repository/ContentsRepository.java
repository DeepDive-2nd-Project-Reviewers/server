package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewers.server.domain.contents.entity.Contents;

@Repository
public interface ContentsRepository extends JpaRepository<Contents, Long> {
}

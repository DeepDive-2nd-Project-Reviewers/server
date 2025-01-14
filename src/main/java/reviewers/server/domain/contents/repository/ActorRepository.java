package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewers.server.domain.contents.entity.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Long> {
}

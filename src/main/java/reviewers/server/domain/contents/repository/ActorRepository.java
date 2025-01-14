package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.contents.entity.Actor;

public interface ActorRepository extends JpaRepository<Actor, Long> {
}

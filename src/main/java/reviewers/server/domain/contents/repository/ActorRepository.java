package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.contents.entity.Actor;

import java.util.List;
import java.util.Set;

public interface ActorRepository extends JpaRepository<Actor, Long> {
    List<Actor> findByActorNameIn(Set<String> names);
}

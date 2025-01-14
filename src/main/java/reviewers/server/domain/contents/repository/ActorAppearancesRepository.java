package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.contents.entity.ActorAppearances;

public interface ActorAppearancesRepository extends JpaRepository<ActorAppearances, Long> {
}
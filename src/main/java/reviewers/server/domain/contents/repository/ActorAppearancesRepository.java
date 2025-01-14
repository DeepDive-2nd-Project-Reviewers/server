package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import reviewers.server.domain.contents.entity.ActorAppearances;

@Repository
public interface ActorAppearancesRepository extends JpaRepository<ActorAppearances, Long> {
}
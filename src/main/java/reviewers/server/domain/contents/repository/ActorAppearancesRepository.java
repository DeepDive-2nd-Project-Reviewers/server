package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reviewers.server.domain.contents.entity.Actor;
import reviewers.server.domain.contents.entity.ActorAppearances;
import reviewers.server.domain.contents.entity.Contents;

import java.util.List;

public interface ActorAppearancesRepository extends JpaRepository<ActorAppearances, Long> {
    @Query("SELECT a.actor FROM ActorAppearances a WHERE a.contents = :contents")
    List<Actor> findAllActorsByContents(@Param("contents") Contents contents);

    void deleteAllByContents(Contents contents);
}
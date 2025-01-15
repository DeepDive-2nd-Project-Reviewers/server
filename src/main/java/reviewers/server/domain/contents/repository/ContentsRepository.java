package reviewers.server.domain.contents.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reviewers.server.domain.contents.entity.Contents;

import java.util.List;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    @Query("SELECT c FROM Contents c WHERE (:category IS NULL OR c.category = :category)")
    List<Contents> findAllByCategory(@Param("category") String category);
}

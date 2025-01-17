package reviewers.server.domain.contents.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import reviewers.server.domain.contents.entity.Category;
import reviewers.server.domain.contents.entity.Contents;

public interface ContentsRepository extends JpaRepository<Contents, Long> {
    @Query("SELECT c FROM Contents c WHERE (:category IS NULL OR c.category = :category)")
    Slice<Contents> findAllByCategory(@Param("category") Category category, Pageable pageable);
}

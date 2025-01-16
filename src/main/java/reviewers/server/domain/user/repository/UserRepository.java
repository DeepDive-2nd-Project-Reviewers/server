package reviewers.server.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import reviewers.server.domain.user.entity.User;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<User> findByEmail(String email);
    Optional<User> findByUserId(Long userId);
    
}

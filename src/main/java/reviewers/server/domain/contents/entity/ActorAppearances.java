package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "actor_appearances")
public class ActorAppearances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
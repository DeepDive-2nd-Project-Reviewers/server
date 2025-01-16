package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "actor_appearances")
public class ActorAppearances {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Contents contents;

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private Actor actor;
}
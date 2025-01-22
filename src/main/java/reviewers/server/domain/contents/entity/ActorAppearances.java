package reviewers.server.domain.contents.entity;

import static jakarta.persistence.FetchType.LAZY;

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
    @Column(name = "actor_appearances_id")
    private Long id;


    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "contents_id")
    private Contents contents;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "actor_id")
    private Actor actor;
}
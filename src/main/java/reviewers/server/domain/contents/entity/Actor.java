package reviewers.server.domain.contents.entity;

import static jakarta.persistence.CascadeType.ALL;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "actor_id")
    private Long id;

    @Column(name = "actor_name", length = 30, nullable = false)
    private String actorName;

    @OneToMany(mappedBy = "actor", cascade = ALL)
    private List<ActorAppearances> actorAppearanceList = new ArrayList<>();
}

package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;
import lombok.*;

@NoArgsConstructor
@Entity
@Table(name = "actor")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}

package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "content_heart")
public class ContentHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "content_id")
    private Contents contents;

//    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User user;
}
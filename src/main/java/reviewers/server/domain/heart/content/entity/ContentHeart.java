package reviewers.server.domain.heart.content.entity;

import static jakarta.persistence.FetchType.LAZY;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.user.entity.User;

@Entity
@NoArgsConstructor
public class ContentHeart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    private Contents content;

    @Builder
    public ContentHeart(User user, Contents content) {
        this.user = user;
        this.content = content;
    }
}

package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import reviewers.server.domain.review.entity.Review;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor
@SuperBuilder
@Table(name = "contents")
public class Contents {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String title;

    private String writer;

    private String summary;

    private String image;

    private long heartCount;

    @OneToMany
    private List<Review> reviews = new ArrayList<>();

    public void updateContents(String category, String title, String writer, String summary, String image) {
        this.category = category;
        this.title = title;
        this.writer = writer;
        this.summary = summary;
        this.image = image;
    }

    public void addHeartCount() {
        this.heartCount++;
    }

    public void subtractHeartCount() {
        this.heartCount--;
    }
}

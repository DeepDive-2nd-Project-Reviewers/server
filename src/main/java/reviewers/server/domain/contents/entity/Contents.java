package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;
import lombok.*;
import reviewers.server.domain.review.entity.Review;

import java.util.ArrayList;
import java.util.List;
import reviewers.server.global.common.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "contents")
public class Contents extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String title;

    private String writer;

    private String summary;

    private String image;

    private long heartCount;

    @OneToMany(mappedBy = "contents", orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    @Builder
    public Contents(Category category, String title, String writer, String summary, String image) {
        this.category = category;
        this.title = title;
        this.writer = writer;
        this.summary = summary;
        this.image = image;
    }

    public void updateContents(Category category, String title, String writer, String summary, String image) {
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

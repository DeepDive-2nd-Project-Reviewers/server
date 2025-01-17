package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;
import lombok.*;
import reviewers.server.global.common.BaseEntity;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "contents")
public class Contents extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String category;

    private String title;

    private String writer;

    private String summary;

    private String image;

    private Long heartCount;

    @Builder
    public Contents(String category, String title, String writer, String summary, String image) {
        this.category = category;
        this.title = title;
        this.writer = writer;
        this.summary = summary;
        this.image = image;
    }

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

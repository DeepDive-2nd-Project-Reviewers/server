package reviewers.server.domain.contents.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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

    public void updateContents(String category, String title, String writer, String summary, String image) {
        this.category = category;
        this.title = title;
        this.writer = writer;
        this.summary = summary;
        this.image = image;
    }
}

package reviewers.server.domain.contents.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import reviewers.server.domain.contents.entity.Category;

@Getter
@Builder
public class ContentsResponseDto {
    private Long contentId;

    private Category category;

    private String title;

    private String writer;

    private String summary;

    private String image;

    private String actor;

    private long heartCount;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}

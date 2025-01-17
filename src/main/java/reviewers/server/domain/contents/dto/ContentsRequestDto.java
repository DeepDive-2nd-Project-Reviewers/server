package reviewers.server.domain.contents.dto;

import lombok.Builder;
import lombok.Getter;
import reviewers.server.domain.contents.entity.Category;

@Getter
@Builder
public class ContentsRequestDto {
    private Category category;

    private String title;

    private String writer;

    private String summary;

    private String actor;
}
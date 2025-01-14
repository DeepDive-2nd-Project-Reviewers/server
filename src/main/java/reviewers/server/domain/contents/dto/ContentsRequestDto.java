package reviewers.server.domain.contents.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ContentsRequestDto {
    private String category;

    private String title;

    private String writer;

    private String summary;

    private String image;

    private String actor;
}
package reviewers.server.domain.contents.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ContentsRequestDto {
    private String category;

    private String title;

    private String writer;

    private String summary;

    private String image;
}
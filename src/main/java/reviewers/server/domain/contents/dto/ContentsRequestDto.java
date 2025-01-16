package reviewers.server.domain.contents.dto;

import lombok.Builder;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
public class ContentsRequestDto {
    private String category;

    private String title;

    private String writer;

    private String summary;

    private String actor;
}
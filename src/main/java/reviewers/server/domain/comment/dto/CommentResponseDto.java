package reviewers.server.domain.comment.dto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@Builder
public class CommentResponseDto {
    private Long id;
    private String content;
    private String nickname;
}

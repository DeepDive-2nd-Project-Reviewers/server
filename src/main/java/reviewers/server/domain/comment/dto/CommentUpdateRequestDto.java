package reviewers.server.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CommentUpdateRequestDto {

    @NotBlank(message = "수정할 댓글 내용을 입력해주세요.")
    private String content;
}
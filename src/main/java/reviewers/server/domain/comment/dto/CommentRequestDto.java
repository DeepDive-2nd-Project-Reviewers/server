package reviewers.server.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.NotNull;

@Data
@NoArgsConstructor
public class CommentRequestDto {

    @NotBlank(message = "댓글 내용을 입력해주세요.")
    private String content;

    @NotNull
    private Long userId;
}

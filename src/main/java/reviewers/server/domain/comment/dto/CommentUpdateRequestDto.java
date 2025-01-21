package reviewers.server.domain.comment.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
public class CommentUpdateRequestDto {

    @NotBlank(message = "수정할 댓글 내용을 입력해주세요.")
    private String content;
}
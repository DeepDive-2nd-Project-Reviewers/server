package reviewers.server.domain.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewers.server.domain.comment.dto.CommentRequestDto;
import reviewers.server.domain.comment.dto.CommentResponseDto;
import reviewers.server.domain.comment.dto.CommentUpdateRequestDto;
import reviewers.server.domain.comment.service.CommentService;
import reviewers.server.global.success.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/reviews/{reviewId}/comments")
    public SuccessResponse<CommentResponseDto> createComment(@PathVariable Long reviewId, @RequestBody CommentRequestDto commentRequestDto) {
        return new SuccessResponse<>(commentService.createComment(reviewId, commentRequestDto));
    }

    @GetMapping("/reviews/{reviewId}/comments")
    public SuccessResponse<List<CommentResponseDto>> getComments(@PathVariable Long reviewId) {
        return new SuccessResponse<>(commentService.findCommentsByReviewId(reviewId));
    }

    @PutMapping("/comments/{commentId}")
    public SuccessResponse<CommentResponseDto> editComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequestDto commentUpdateRequestDto) {
        return new SuccessResponse<>(commentService.updateComment(commentId, commentUpdateRequestDto));
    }

    @DeleteMapping("/comments/{commentId}")
    public SuccessResponse<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return SuccessResponse.ok("삭제 성공");
    }
}

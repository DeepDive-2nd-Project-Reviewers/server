package reviewers.server.domain.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewers.server.domain.comment.dto.CommentRequest;
import reviewers.server.domain.comment.dto.CommentResponse;
import reviewers.server.domain.comment.dto.CommentUpdateRequest;
import reviewers.server.domain.comment.service.CommentService;
import reviewers.server.global.success.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/review/{reviewId}")
    public SuccessResponse<CommentResponse> createComment(@PathVariable Long reviewId, @RequestBody CommentRequest commentRequest) {
        return new SuccessResponse<>(commentService.createComment(reviewId, commentRequest));
    }

    @GetMapping("/review/{reviewId}")
    public SuccessResponse<List<CommentResponse>> getComments(@PathVariable Long reviewId) {
        return new SuccessResponse<>(commentService.findCommentsByReviewId(reviewId));
    }

    @PutMapping("/{commentId}")
    public SuccessResponse<CommentResponse> editComment(@PathVariable Long commentId, @RequestBody CommentUpdateRequest commentUpdateRequest) {
        return new SuccessResponse<>(commentService.updateComment(commentId, commentUpdateRequest));
    }

    @DeleteMapping("/{commentId}")
    public SuccessResponse<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return SuccessResponse.ok("삭제 성공");
    }
}

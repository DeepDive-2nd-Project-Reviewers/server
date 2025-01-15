package reviewers.server.domain.comment.controller;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewers.server.domain.comment.dto.CommentRequest;
import reviewers.server.domain.comment.dto.CommentResponse;
import reviewers.server.domain.comment.service.CommentService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<CommentResponse> createComment(@PathVariable Long reviewId, @RequestBody CommentRequest commentRequest) {
        return ResponseEntity.ok(commentService.createComment(reviewId, commentRequest));
    }

    @GetMapping("/review/{reviewId}")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable Long reviewId) {
        return ResponseEntity.ok(commentService.findCommentsByReviewId(reviewId));
    }

}

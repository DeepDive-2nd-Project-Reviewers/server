package reviewers.server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import reviewers.server.domain.comment.dto.CommentRequest;
import reviewers.server.domain.comment.dto.CommentResponse;
import reviewers.server.domain.comment.entity.Comment;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.user.entity.User;

@Component
public class CommentConverter {

    // request -> entity
    public Comment toEntity(CommentRequest request, User user, Review review) {
        return Comment.builder()
                .content(request.getContent())
                .user(user)
                .review(review)
                .build();
    }

    // entity -> response
    public CommentResponse toResponse(Comment comment) {
        return CommentResponse.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getUsername())
                .build();
    }
}

package reviewers.server.domain.comment.service;

import org.springframework.stereotype.Component;
import reviewers.server.domain.comment.dto.CommentRequestDto;
import reviewers.server.domain.comment.dto.CommentResponseDto;
import reviewers.server.domain.comment.entity.Comment;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.user.entity.User;

@Component
public class CommentConverter {

    // request -> entity
    public Comment toEntity(CommentRequestDto request, User user, Review review) {
        return Comment.builder()
                .content(request.getContent())
                .user(user)
                .review(review)
                .build();
    }

    // entity -> response
    public CommentResponseDto toResponse(Comment comment) {
        return CommentResponseDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .nickname(comment.getUser().getUsername())
                .build();
    }
}

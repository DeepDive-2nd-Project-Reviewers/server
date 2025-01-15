package reviewers.server.domain.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.comment.dto.CommentResponse;
import reviewers.server.domain.comment.dto.CommentUpdateRequest;
import reviewers.server.domain.comment.entity.Comment;
import reviewers.server.domain.comment.dto.CommentRequest;
import reviewers.server.domain.comment.repository.CommentRepository;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.repository.ReviewInterface;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.repository.UserRepository;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final ReviewInterface reviewRepository;
    private final CommentConverter commentConverter;

    private User findUser(CommentRequest request) {
        return userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));
    }

    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_REVIEW));
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_COMMENT));
    }

    private void checkCommentsEmpty(List<Comment> comments) {
        if(comments.isEmpty()){
            throw new BaseErrorException(ErrorType._NOT_FOUND_COMMENT);
        }
    }

    @Transactional
    public CommentResponse createComment(Long reviewId, CommentRequest request) {
        User user = findUser(request);
        Review review = findReview(reviewId);

        Comment comment = commentConverter.toEntity(request, user, review);
        Comment savedComment = commentRepository.save(comment);
        return commentConverter.toResponse(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> findCommentsByReviewId(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewId(reviewId);

        checkCommentsEmpty(comments);

        return comments.stream()
                .map(commentConverter::toResponse)
                .toList();
    }

    @Transactional
    public CommentResponse updateComment(Long commentId, CommentUpdateRequest commentUpdateRequest) {
        Comment comment = findComment(commentId);

        comment.updateContent(commentUpdateRequest.getContent());
        return commentConverter.toResponse(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = findComment(commentId);
        commentRepository.delete(comment);
    }
}
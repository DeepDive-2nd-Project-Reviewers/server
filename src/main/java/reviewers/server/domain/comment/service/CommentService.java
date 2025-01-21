package reviewers.server.domain.comment.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.comment.dto.CommentResponseDto;
import reviewers.server.domain.comment.dto.CommentUpdateRequestDto;
import reviewers.server.domain.comment.entity.Comment;
import reviewers.server.domain.comment.dto.CommentRequestDto;
import reviewers.server.domain.comment.repository.CommentRepository;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.service.ReviewService;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.service.UserService;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentConverter commentConverter;
    private final UserService userService;
    private final ReviewService reviewService;

    public CommentResponseDto createComment(Long reviewId, CommentRequestDto request) {
        User user = userService.findUser();
        Review review = reviewService.findReview(reviewId);

        Comment comment = commentConverter.toEntity(request, user, review);
        Comment savedComment = commentRepository.save(comment);
        return commentConverter.toResponse(savedComment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> findCommentsByReviewId(Long reviewId) {
        List<Comment> comments = commentRepository.findByReviewId(reviewId);

        checkCommentsEmpty(comments);

        return comments.stream()
                .map(commentConverter::toResponse)
                .toList();
    }

    public CommentResponseDto updateComment(Long commentId, CommentUpdateRequestDto commentUpdateRequestDto) {

        User user = userService.findUser();
        Comment comment = findComment(commentId);
        checkIfReviewMine(user, commentId);

        comment.updateContent(commentUpdateRequestDto.getContent());
        return commentConverter.toResponse(comment);
    }

    public void deleteComment(Long commentId) {

        User user = userService.findUser();
        Comment comment = findComment(commentId);
        checkIfReviewMine(user, commentId);
        commentRepository.delete(comment);
    }

    private void checkIfReviewMine(User user, Long commentId) {
        if(user.getUserId().equals(commentId)) {
            return;
        }
        throw new BaseErrorException(ErrorType._UNAUTHORIZED_USER);
    }

    private Comment findComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_COMMENT));
    }

    private void checkCommentsEmpty(List<Comment> comments) {
        if(comments.isEmpty()){
            throw new BaseErrorException(ErrorType._EMPTY_COMMENT);
        }
    }
}
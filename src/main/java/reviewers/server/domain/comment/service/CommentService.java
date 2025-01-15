package reviewers.server.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewers.server.domain.comment.dto.CommentResponse;
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

    private Review findReview(CommentRequest request) {
        return reviewRepository.findById(request.getReviewId())
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_REVIEW));
    }

}
package reviewers.server.domain.heart.review.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.heart.review.dto.ReviewHeartRequestDto;
import reviewers.server.domain.heart.review.entity.ReviewHeart;
import reviewers.server.domain.heart.review.repository.ReviewHeartRepository;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.repository.ReviewRepository;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.repository.UserRepository;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewHeartService {

    private final ReviewHeartRepository reviewHeartRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;

    private User findUser(ReviewHeartRequestDto request) {
        return userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_USER));
    }

    private Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_USER));
    }

    private void checkIfAlreadyLiked(User user, Review review) {
        if (reviewHeartRepository.existsByUserAndReview(user, review)) {
            throw new BaseErrorException(ErrorType._ALREADY_LIKE);
        }
    }

    private void checkIfNotLiked(User user, Review review) {
        if (!reviewHeartRepository.existsByUserAndReview(user, review)) {
            throw new BaseErrorException(ErrorType._NOT_LIKE);
        }
    }

    public void createHeart(Long contentId, ReviewHeartRequestDto request) {
        User user = findUser(request);
        Review review = findReview(contentId);

        checkIfAlreadyLiked(user, review);

        ReviewHeart reviewHeart = ReviewHeart.builder()
                .user(user)
                .review(review)
                .build();
        reviewHeartRepository.save(reviewHeart);
        review.addHeartCount();
    }

    public void deleteHeart(Long contentId, ReviewHeartRequestDto request) {
        User user = findUser(request);
        Review review = findReview(contentId);

        checkIfNotLiked(user, review);

        reviewHeartRepository.deleteByUserAndReview(user, review);
        review.subtractHeartCount();
    }
}
package reviewers.server.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.repository.ContentsRepository;
import reviewers.server.domain.review.dto.ReviewDetailResponseDto;
import reviewers.server.domain.review.dto.ReviewRequestDto;
import reviewers.server.domain.review.dto.ReviewResponseDto;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.repository.ReviewRepository;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.service.UserService;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ContentsRepository contentsRepository;
    private final ReviewRepository reviewRepository;
    private final UserService userService;

    public void create(Long contentsId, ReviewRequestDto requestDto) {

        User user = userService.findUser();

        Contents contents = checkIfContentsExists(contentsId);
        Long count = Long.valueOf(requestDto.getStarCount());

        Review result = new Review(requestDto, count, contents, user);
        reviewRepository.save(result);
    }

    @Transactional(readOnly = true)
    public Page<ReviewResponseDto> getAllReview(Long contentsId, Pageable pageable) {
        Contents contents = checkIfContentsExists(contentsId);
        List<Review> reviews = reviewRepository.findAllByContents(contents, pageable);
        List<ReviewResponseDto> result = reviews.stream()
                .map(review -> new ReviewResponseDto(review.getId(), review.getTitle(), review.getUpdatedAt())).toList();

        log.info("size: {}", reviews.size());
        return new PageImpl<>(result, pageable, reviews.size());
    }

    @Transactional(readOnly = true)
    public ReviewDetailResponseDto getReview(Long contentsId, Long reviewId) {
        checkIfContentsExists(contentsId);
        Review review = checkIfReviewExist(reviewId);

        return new ReviewDetailResponseDto(review);
    }

    @Transactional
    public void updateReview(Long contentsId, Long reviewId, ReviewRequestDto requestDto) {
        checkIfContentsExists(contentsId);
        Review review = checkIfReviewExist(reviewId);
        Long count = Long.valueOf(requestDto.getStarCount());

        review.updateReview(requestDto, count);
    }

    public void deleteReview(Long contentsId, Long reviewId) {
        checkIfContentsExists(contentsId);
        Review review = checkIfReviewExist(reviewId);
        reviewRepository.delete(review);
    }

    private Contents checkIfContentsExists(Long contentsId) {
        return contentsRepository.findById(contentsId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));
    }

    private Review checkIfReviewExist(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_REVIEW));
    }

    public Review findReview(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_REVIEW));
    }
}
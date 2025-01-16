package reviewers.server.domain.review.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.repository.ContentsRepository;
import reviewers.server.domain.review.dto.ReviewRequestDto;
import reviewers.server.domain.review.dto.ReviewResponseDto;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.repository.ReviewRepository;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ContentsRepository contentsRepository;
    private final ReviewRepository reviewRepository;

    public void create(Long contentsId, ReviewRequestDto requestDto) {
        checkIfContentsExists(contentsId);
        Long count = Long.valueOf(requestDto.getStarCount());
        Review result = new Review(requestDto, count);
        reviewRepository.save(result);
    }

    public void getAllReview(Long contentsId) {
        Contents contents = checkIfContentsExists(contentsId);
        List<Review> reviews = reviewRepository.findAllByContents(contents);
        log.info("size: {}", reviews.size());

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

    public Contents checkIfContentsExists(Long contentsId) {
        return contentsRepository.findById(contentsId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));
    }

    private Review checkIfReviewExist(Long reviewId) {
        return reviewRepository.findById(reviewId)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_REVIEW));
    }


}
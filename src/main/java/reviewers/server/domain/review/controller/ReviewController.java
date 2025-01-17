package reviewers.server.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import reviewers.server.domain.review.dto.ReviewDetailResponseDto;
import reviewers.server.domain.review.dto.ReviewRequestDto;
import reviewers.server.domain.review.dto.ReviewResponseDto;
import reviewers.server.domain.review.service.ReviewService;
import reviewers.server.global.success.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/contents/{contentId}/reviews")
    public SuccessResponse createReview(@PathVariable Long contentId,
                                        @Valid @RequestBody ReviewRequestDto dto){
        reviewService.create(contentId, dto);
        return new SuccessResponse("Review created");
    }

    @GetMapping("/contents/{contentId}/reviews")
    public SuccessResponse<Page<ReviewResponseDto>> getAllReviews(@PathVariable Long contentId,
                                                                  @PageableDefault(sort = "id", size = 8) Pageable pageable){

        return new SuccessResponse<>(reviewService.getAllReview(contentId, pageable));
    }

    @GetMapping("/contents/{contentId}/reviews/{reviewId}")
    public SuccessResponse<ReviewDetailResponseDto> getReview(@PathVariable Long contentId,
                                                              @PathVariable Long reviewId){

        return new SuccessResponse<>(reviewService.getReview(contentId, reviewId));
    }

    @PutMapping("/contents/{contentId}/reviews/{reviewId}")
    public SuccessResponse updateReview(@PathVariable Long contentId,
                                        @PathVariable Long reviewId,
                                        @Valid @RequestBody ReviewRequestDto dto){

        reviewService.updateReview(contentId, reviewId, dto);
        return new SuccessResponse("Review updated");
    }

    @DeleteMapping("/contents/{contentId}/reviews/{reviewId}")
    public SuccessResponse deleteReview(@PathVariable Long contentId,
                                        @PathVariable Long reviewId){
        reviewService.deleteReview(contentId, reviewId);

        return new SuccessResponse("Review deleted");
    }
}

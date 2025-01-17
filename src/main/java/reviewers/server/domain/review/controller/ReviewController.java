package reviewers.server.domain.review.controller;

import jakarta.validation.Valid;
import lombok.Getter;
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
@RequestMapping("/api/v1/review")
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/{contentsId}")
    public SuccessResponse createReview(@PathVariable Long contentsId,
                                        @Valid @RequestBody ReviewRequestDto dto){
        reviewService.create(contentsId, dto);
        return new SuccessResponse("Review created");
    }

    @GetMapping("/{contentsId}")
    public SuccessResponse<Page<ReviewResponseDto>> getAllReviews(@PathVariable Long contentsId,
                                                                  @PageableDefault(sort = "id", size = 8) Pageable pageable){

        return new SuccessResponse<>(reviewService.getAllReview(contentsId, pageable));
    }

    @GetMapping("/{contentsId}/{reviewId}")
    public SuccessResponse<ReviewDetailResponseDto> getReview(@PathVariable Long contentsId,
                                                              @PathVariable Long reviewId){

        return new SuccessResponse<>(reviewService.getReview(contentsId, reviewId));
    }

    @PutMapping("/{contentsId}/{reviewId}")
    public SuccessResponse updateReview(@PathVariable Long contentsId,
                                        @PathVariable Long reviewId,
                                        @Valid @RequestBody ReviewRequestDto dto){

        reviewService.updateReview(contentsId, reviewId, dto);
        return new SuccessResponse("Review updated");
    }

    @DeleteMapping("/{contentsId}/{reviewId}")
    public SuccessResponse deleteReview(@PathVariable Long contentsId,
                                        @PathVariable Long reviewId){
        reviewService.deleteReview(contentsId, reviewId);

        return new SuccessResponse("Review deleted");
    }
}

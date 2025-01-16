package reviewers.server.domain.heart.review.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewers.server.domain.heart.review.dto.ReviewHeartRequestDto;
import reviewers.server.domain.heart.review.service.ReviewHeartService;
import reviewers.server.global.success.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ReviewHeartController {

    private final ReviewHeartService heartService;

    @PostMapping("/reviews/{reviewId}/hearts")
    public SuccessResponse<Void> createHeart(@PathVariable Long reviewId, @RequestBody ReviewHeartRequestDto requestDto) {
        heartService.createHeart(reviewId, requestDto);
        return SuccessResponse.ok("좋아요 성공");
    }

    @DeleteMapping("/reviews/{reviewId}/hearts")
    public SuccessResponse<Void> deleteHeart(@PathVariable Long reviewId, @RequestBody ReviewHeartRequestDto requestDto) {
        heartService.deleteHeart(reviewId, requestDto);
        return SuccessResponse.ok("좋아요 취소 성공");
    }
}

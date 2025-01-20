package reviewers.server.domain.heart.content.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewers.server.domain.heart.content.dto.ContentHeartRequestDto;
import reviewers.server.domain.heart.content.service.ContentHeartService;
import reviewers.server.global.success.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ContentHeartController {

    private final ContentHeartService heartService;

    @PostMapping("/contents/{contentId}/hearts")
    public SuccessResponse<Void> createHeart(@PathVariable Long contentId) {
        heartService.createHeart(contentId);
        return SuccessResponse.ok("좋아요 성공");
    }

    @DeleteMapping("/contents/{contentId}/hearts")
    public SuccessResponse<Void> deleteHeart(@PathVariable Long contentId) {
        heartService.deleteHeart(contentId);
        return SuccessResponse.ok("좋아요 취소 성공");
    }
}

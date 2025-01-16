package reviewers.server.domain.contents.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.service.ContentsService;
import reviewers.server.global.success.SuccessResponse;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contents")
public class ContentsController {

    private final ContentsService contentsService;

    @PostMapping
    public SuccessResponse<ContentsResponseDto> create(@RequestPart("dto") ContentsRequestDto dto,
                                                       @RequestPart(value = "image", required = false) MultipartFile image){
        ContentsResponseDto response = contentsService.create(dto, image);
        return new SuccessResponse<>(response);
    }

    @GetMapping
    public SuccessResponse<Slice<ContentsResponseDto>> getAll(@RequestParam(value = "category", required = false) String category,
                                                              @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Slice<ContentsResponseDto> response = contentsService.readAllByCategory(category, pageable);
        return new SuccessResponse<>(response);
    }

    @GetMapping("/{contentId}")
    public SuccessResponse<ContentsResponseDto> getById(@PathVariable Long contentId){
        ContentsResponseDto response = contentsService.readByContentId(contentId);
        return new SuccessResponse<>(response);
    }

    @PutMapping("/{contentId}")
    public SuccessResponse<ContentsResponseDto> update(@PathVariable Long contentId, @RequestPart ContentsRequestDto dto,
                                                       @RequestPart(value = "image", required = false) MultipartFile image){
        ContentsResponseDto response = contentsService.update(contentId, dto, image);
        return new SuccessResponse<>(response);
    }

    @DeleteMapping("/{contentId}")
    public SuccessResponse<Void> delete(@PathVariable Long contentId){
        contentsService.deleteById(contentId);
        return SuccessResponse.ok();
    }
}

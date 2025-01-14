package reviewers.server.domain.contents.controller;

import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.service.ContentsService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/contents")
public class ContentsController {

    private final ContentsService contentsService;

    @PostMapping
    public ResponseEntity<ContentsResponseDto> create(ContentsRequestDto dto){
        ContentsResponseDto response = contentsService.create(dto);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ContentsResponseDto>> getAll(@PathParam("category") String category) {
        List<ContentsResponseDto> response = contentsService.readAllByCategory(category);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{contentId}")
    public ResponseEntity<ContentsResponseDto> getById(@PathVariable Long contentId){
        ContentsResponseDto response = contentsService.readByContentId(contentId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{contentId}")
    public ResponseEntity<ContentsResponseDto> update(@PathVariable Long contentId, ContentsRequestDto dto){
        ContentsResponseDto response = contentsService.update(contentId, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{contentId}")
    public ResponseEntity<Void> delete(@PathVariable Long contentId){
        contentsService.deleteById(contentId);
        return ResponseEntity.noContent().build();
    }
}

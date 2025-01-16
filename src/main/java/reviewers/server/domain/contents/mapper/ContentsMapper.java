package reviewers.server.domain.contents.mapper;

import org.springframework.stereotype.Component;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.entity.Contents;

@Component
public class ContentsMapper {
    public Contents toEntity(ContentsRequestDto dto){
        return Contents.builder()
                .category(dto.getCategory())
                .title(dto.getTitle())
                .writer(dto.getWriter())
                .summary(dto.getSummary())
                .image(dto.getImage())
                .build();
    }

    public ContentsResponseDto toDto(Contents contents, String actors){
        return ContentsResponseDto.builder()
                .contentId(contents.getId())
                .category(contents.getCategory())
                .title(contents.getTitle())
                .writer(contents.getWriter())
                .summary(contents.getSummary())
                .image(contents.getImage())
                .actor(actors)
                .heartCount(contents.getHeartCount())
                .build();
    }
}

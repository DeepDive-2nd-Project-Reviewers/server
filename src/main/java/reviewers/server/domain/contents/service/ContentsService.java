package reviewers.server.domain.contents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.mapper.ContentsMapper;
import reviewers.server.domain.contents.repository.ContentsRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContentsService {

    private final ContentsRepository contentsRepository;
    private final ContentsMapper contentsMapper;

    public ContentsResponseDto create(ContentsRequestDto contentsRequestDto) {
        Contents contents = contentsMapper.toEntity(contentsRequestDto);
        return contentsMapper.toDto(contentsRepository.save(contents));
    }

    public ContentsResponseDto update(Long id, ContentsRequestDto request) {
        Contents content = contentsRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Content not found"));

        content.setCategory(request.getCategory());
        content.setTitle(request.getTitle());
        content.setWriter(request.getWriter());
        content.setSummary(request.getSummary());
        content.setImage(request.getImage());

        Contents updated = contentsRepository.save(content);
        return contentsMapper.toDto(updated);
    }

    public List<ContentsResponseDto> readAllByCategory(String category) {
        return contentsRepository.findAllByCategory(category).stream()
                .map(contentsMapper::toDto)
                .toList();
    }

    public ContentsResponseDto readByContentId(Long id) {
        Contents content = contentsRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Content not found"));

        return contentsMapper.toDto(content);
    }

    public void deleteById(Long id) {
        contentsRepository.findById(id)
                .orElseThrow(() -> new NullPointerException("Content not found"));
        contentsRepository.deleteById(id);
    }
}

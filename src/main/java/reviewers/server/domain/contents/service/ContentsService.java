package reviewers.server.domain.contents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.mapper.ContentsMapper;
import reviewers.server.domain.contents.repository.ContentsRepository;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentsService {

    private final ContentsRepository contentsRepository;
    private final ContentsMapper contentsMapper;
    private final ActorService actorService;

    public ContentsResponseDto create(ContentsRequestDto contentsRequestDto) {
        Contents contents = contentsMapper.toEntity(contentsRequestDto);
        Contents saved = contentsRepository.save(contents);
        actorService.create(contentsRequestDto.getActor(), saved);
        return toDto(saved);
    }

    public Slice<ContentsResponseDto> readAllByCategory(String category, Pageable pageable) {
        if(category != null && !category.equals("book") && !category.equals("movie")) {
            throw new BaseErrorException(ErrorType._NOT_FOUND_CATEGORY);
        }

        Slice<Contents> contents = contentsRepository.findAllByCategory(category, pageable);
        return contents.map(this::toDto);
    }

    public ContentsResponseDto readByContentId(Long id) {
        Contents content = contentsRepository.findById(id)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));
        return toDto(content);
    }

    public ContentsResponseDto update(Long id, ContentsRequestDto request) {
        Contents content = contentsRepository.findById(id)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));

        content.updateContents(request.getCategory(), request.getTitle(), request.getWriter(), request.getSummary(), request.getImage());
        return toDto(contentsRepository.save(content));
    }

    public void deleteById(Long id) {
        contentsRepository.findById(id)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));
        contentsRepository.deleteById(id);
    }

    private ContentsResponseDto toDto(Contents contents) {
        String actors = actorService.getAllActorsByContents(contents);
        return contentsMapper.toDto(contents, actors);
    }
}

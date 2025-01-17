package reviewers.server.domain.contents.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.entity.Category;
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
    private final ActorAppearancesService actorAppearancesService;
    private final FileUploadService fileUploadService;

    public ContentsResponseDto create(ContentsRequestDto contentsRequestDto, MultipartFile image) {
        String url = processImage(image);
        Contents contents = contentsMapper.toEntity(contentsRequestDto, url);
        Contents saved = contentsRepository.save(contents);
        actorService.create(contentsRequestDto.getActor(), saved);
        return toDto(saved);
    }

    public Slice<ContentsResponseDto> readAllByCategory(Category category, Pageable pageable) {
        if (category != null && category != Category.BOOK && category != Category.MOVIE) {
            throw new BaseErrorException(ErrorType._NOT_FOUND_CATEGORY);
        }

        Slice<Contents> contents = contentsRepository.findAllByCategory(category, pageable);
        return contents.map(this::toDto);
    }

    public ContentsResponseDto readByContentId(Long id) {
        Contents content = findById(id);
        return toDto(content);
    }

    public ContentsResponseDto update(Long id, ContentsRequestDto request, MultipartFile image) {
        Contents content = findById(id);

        actorAppearancesService.deleteByContents(content);
        String url = processImage(content.getImage(), image);
        content.updateContents(request.getCategory(), request.getTitle(), request.getWriter(), request.getSummary(),
                url);
        actorService.create(request.getActor(), content);
        return toDto(contentsRepository.save(content));
    }

    public void deleteById(Long id) {
        Contents contents = findById(id);
        actorAppearancesService.deleteByContents(contents);
        contentsRepository.deleteById(id);
    }

    private ContentsResponseDto toDto(Contents contents) {
        String actors = actorService.getAllActorsByContents(contents);
        return contentsMapper.toDto(contents, actors);
    }

    public Contents findById(Long id) {
        return contentsRepository.findById(id)
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));
    }

    private String processImage(MultipartFile image) {
        if (image != null) {
            return fileUploadService.upload(image);
        }
        return null;
    }

    private String processImage(String url, MultipartFile image) {
        if (url != null && !url.isEmpty()) {
            fileUploadService.deleteImageByUrl(url);
        }
        return processImage(image);
    }
}

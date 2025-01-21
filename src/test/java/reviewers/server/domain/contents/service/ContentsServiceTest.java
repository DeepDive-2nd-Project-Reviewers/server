package reviewers.server.domain.contents.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.entity.Category;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.mapper.ContentsMapper;
import reviewers.server.domain.contents.repository.ActorRepository;
import reviewers.server.domain.contents.repository.ContentsRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ContentsServiceTest {
    @Mock
    private ContentsRepository contentsRepository;

    @Mock
    private ActorRepository actorRepository;

    @Mock
    private ContentsMapper contentsMapper;

    @Mock
    private ActorAppearancesService actorAppearancesService;

    @Mock
    private ActorService actorService;

    @Mock
    private FileUploadService fileUploadService;

    @InjectMocks
    private ContentsService contentsService;

    private ContentsRequestDto requestDto;
    private ContentsResponseDto responseDto;
    private Contents contentsEntity1;
    private Contents contentsEntity2;

    @BeforeEach
    void setUp() {
        requestDto = ContentsRequestDto.builder()
                .category(Category.MOVIE)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .actor("Sample Actor")
                .build();

        responseDto = ContentsResponseDto.builder()
                .contentId(1L)
                .category(Category.MOVIE)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .actor("Sample Actor")
                .heartCount(0L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        contentsEntity1 = Contents.builder()
                .category(Category.MOVIE)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .image("sample-image-url")
                .build();

        contentsEntity2 = Contents.builder()
                .category(Category.BOOK)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .image("sample-image-url")
                .build();
    }

    @Test
    @DisplayName("이미지 없이 새로운 컨텐츠를 생성한다.")
    void createWithoutImageTest() {
        when(contentsMapper.toEntity(requestDto, null)).thenReturn(contentsEntity1);
        when(contentsRepository.save(contentsEntity1)).thenReturn(contentsEntity1);
        when(actorService.getAllActorsByContents(contentsEntity1)).thenReturn("Sample Actor");
        when(contentsMapper.toDto(contentsEntity1, "Sample Actor")).thenReturn(responseDto);

        ContentsResponseDto result = contentsService.create(requestDto, null);

        Assertions.assertNotNull(result, "Result should not be null");
        Assertions.assertEquals(responseDto.getTitle(), result.getTitle(), "Title should match");
        Assertions.assertEquals(responseDto.getWriter(), result.getWriter(), "Writer should match");
        Assertions.assertEquals(responseDto.getActor(), result.getActor(), "Actor should match");
        Assertions.assertNull(result.getImage(), "Image should be null");

        verify(contentsMapper, times(1)).toEntity(requestDto, null);
        verify(contentsRepository, times(1)).save(contentsEntity1);
        verify(actorService, times(1)).getAllActorsByContents(contentsEntity1);
        verify(contentsMapper, times(1)).toDto(contentsEntity1, "Sample Actor");
    }

    @Test
    @DisplayName("컨텐츠를 카테고리 별로 조회한다.")
    void readAllByCategoryTest() {
        Pageable pageable = PageRequest.of(0, 8, Sort.by("id").ascending());
        Slice<Contents> contentsSlice = new SliceImpl<>(List.of(contentsEntity1));

        when(contentsRepository.findAllByCategory(Category.MOVIE, pageable)).thenReturn(contentsSlice);
        when(actorService.getAllActorsByContents(contentsEntity1)).thenReturn("Sample Actor");
        when(contentsMapper.toDto(contentsEntity1, "Sample Actor")).thenReturn(responseDto);

        Slice<ContentsResponseDto> result = contentsService.readAllByCategory(Category.MOVIE, pageable);

        Assertions.assertEquals(1, result.getContent().size(), "Slice content size should be 1");
        Assertions.assertEquals(responseDto.getCategory(), result.getContent().get(0).getCategory(), "Category should match");
        Assertions.assertEquals(responseDto.getTitle(), result.getContent().get(0).getTitle(), "Title should match");
        Assertions.assertEquals(responseDto.getActor(), result.getContent().get(0).getActor(), "Actor should match");

        verify(contentsRepository, times(1)).findAllByCategory(Category.MOVIE, pageable);
        verify(actorService, times(1)).getAllActorsByContents(contentsEntity1);
        verify(contentsMapper, times(1)).toDto(contentsEntity1, "Sample Actor");
    }


    @Test
    @DisplayName("컨텐츠를 컨텐츠 아이디를 통해 조회한다.")
    void readByContentIdTest() {
        when(contentsRepository.findById(1L)).thenReturn(Optional.of(contentsEntity1));

        Contents result = contentsService.findById(1L);

        Assertions.assertEquals(contentsEntity1.getId(), result.getId(), "IDs should match");
        Assertions.assertEquals(contentsEntity1.getTitle(), result.getTitle(), "Titles should match");
        Assertions.assertEquals(contentsEntity1.getCategory(), result.getCategory(), "Categories should match");

        verify(contentsRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("컨텐츠를 삭제한다.")
    void deleteByIdTest() {
        when(contentsRepository.findById(1L)).thenReturn(Optional.of(contentsEntity1));

        contentsService.deleteById(1L);

        verify(contentsRepository, times(1)).findById(1L);
        verify(actorAppearancesService, times(1)).deleteByContents(contentsEntity1);
        verify(contentsRepository, times(1)).deleteById(1L);
    }
}

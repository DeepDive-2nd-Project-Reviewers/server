package reviewers.server.domain.contents.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.SliceImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import reviewers.server.domain.contents.dto.ContentsRequestDto;
import reviewers.server.domain.contents.dto.ContentsResponseDto;
import reviewers.server.domain.contents.entity.Category;
import reviewers.server.domain.contents.mapper.ContentsMapper;
import reviewers.server.domain.contents.service.ContentsService;
import reviewers.server.global.config.WebSecurityConfig;
import reviewers.server.global.success.SuccessResponse;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(value = ContentsController.class, excludeFilters = {
        @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {WebSecurityConfig.class}
        )
})
@AutoConfigureMockMvc(addFilters = false)

class ContentsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContentsService contentsService;

    @Test
    @DisplayName("새로운 컨텐츠를 추가한다.")
    void createTest() throws Exception {
        ContentsRequestDto requestDto = ContentsRequestDto.builder()
                .category(Category.MOVIE)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .actor("Sample Actor")
                .build();

        ContentsResponseDto responseDto = ContentsResponseDto.builder()
                .contentId(1L)
                .category(Category.MOVIE)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .actor("Sample Actor")
                .image("sample-image-url")
                .heartCount(100L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(contentsService.create(Mockito.any(), Mockito.any()))
                .thenReturn(responseDto);

        MockMultipartFile jsonPart = new MockMultipartFile(
                "dto",
                "",
                "application/json",
                objectMapper.writeValueAsString(requestDto).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile imagePart = new MockMultipartFile(
                "image",
                "image.png",
                "image/png",
                "Sample Image".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/contents")
                        .file(jsonPart)
                        .file(imagePart)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다!"))
                .andExpect(jsonPath("$.result.contentId").value(1L))
                .andExpect(jsonPath("$.result.title").value("Sample Title"))
                .andExpect(jsonPath("$.result.writer").value("Sample Writer"));
    }

    @Test
    @DisplayName("컨텐츠를 조회한다.")
    void getAllTest() throws Exception {
        ContentsResponseDto responseDto = ContentsResponseDto.builder()
                .contentId(1L)
                .category(Category.MOVIE)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .actor("Sample Actor")
                .image("sample-image-url")
                .heartCount(100L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        List<ContentsResponseDto> responseList = List.of(responseDto);
        SliceImpl<ContentsResponseDto> responseSlice = new SliceImpl<>(responseList);

        Mockito.when(contentsService.readAllByCategory(Mockito.any(), Mockito.any()))
                .thenReturn(responseSlice);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contents")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다!"))
                .andExpect(jsonPath("$.result.content[0].contentId").value(1L))
                .andExpect(jsonPath("$.result.content[0].title").value("Sample Title"))
                .andExpect(jsonPath("$.result.content[0].writer").value("Sample Writer"))
                .andExpect(jsonPath("$.result.content[0].category").value("MOVIE"));
    }

    @Test
    @DisplayName("컨텐츠를 아이디로 조회한다.")
    void getByIdTest() throws Exception {
        ContentsResponseDto responseDto = ContentsResponseDto.builder()
                .contentId(1L)
                .category(Category.MOVIE)
                .title("Sample Title")
                .writer("Sample Writer")
                .summary("Sample Summary")
                .actor("Sample Actor")
                .image("sample-image-url")
                .heartCount(100L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(contentsService.readByContentId(1L))
                .thenReturn(responseDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/contents/{contentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다!"))
                .andExpect(jsonPath("$.result.contentId").value(1L))
                .andExpect(jsonPath("$.result.title").value("Sample Title"))
                .andExpect(jsonPath("$.result.writer").value("Sample Writer"))
                .andExpect(jsonPath("$.result.category").value("MOVIE"));
    }

    @Test
    @DisplayName("컨텐츠를 수정한다.")
    void updateTest() throws Exception {
        ContentsRequestDto requestDto = ContentsRequestDto.builder()
                .category(Category.MOVIE)
                .title("Updated Title")
                .writer("Updated Writer")
                .summary("Updated Summary")
                .actor("Updated Actor")
                .build();

        ContentsResponseDto responseDto = ContentsResponseDto.builder()
                .contentId(1L)
                .category(Category.MOVIE)
                .title("Updated Title")
                .writer("Updated Writer")
                .summary("Updated Summary")
                .actor("Updated Actor")
                .image("updated-image-url")
                .heartCount(200L)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Mockito.when(contentsService.update(Mockito.eq(1L), Mockito.any(), Mockito.any()))
                .thenReturn(responseDto);

        MockMultipartFile jsonPart = new MockMultipartFile(
                "dto",
                "",
                "application/json",
                objectMapper.writeValueAsString(requestDto).getBytes(StandardCharsets.UTF_8)
        );

        MockMultipartFile imagePart = new MockMultipartFile(
                "image",
                "updated-image.png",
                "image/png",
                "Updated Image".getBytes()
        );

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/v1/contents/{contentId}", 1L)
                        .file(jsonPart)
                        .file(imagePart)
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("요청에 성공하였습니다!"))
                .andExpect(jsonPath("$.result.contentId").value(1L))
                .andExpect(jsonPath("$.result.title").value("Updated Title"))
                .andExpect(jsonPath("$.result.writer").value("Updated Writer"));
    }

    @Test
    @DisplayName("컨텐츠를 삭제한다.")
    void deleteTest() throws Exception {
        Mockito.doNothing().when(contentsService).deleteById(1L);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/contents/{contentId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(200))
                .andExpect(jsonPath("$.message").value("SUCCESS"));
    }
}
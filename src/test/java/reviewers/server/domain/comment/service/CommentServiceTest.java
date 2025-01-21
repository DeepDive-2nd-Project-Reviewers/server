package reviewers.server.domain.comment.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reviewers.server.domain.comment.dto.CommentRequestDto;
import reviewers.server.domain.comment.dto.CommentResponseDto;
import reviewers.server.domain.comment.entity.Comment;
import reviewers.server.domain.comment.repository.CommentRepository;
import reviewers.server.domain.contents.entity.Category;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.review.dto.ReviewRequestDto;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.service.ReviewService;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.service.UserService;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {

    @InjectMocks
    private CommentService commentService;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private CommentConverter commentConverter;

    @Mock
    private UserService userService;

    @Mock
    private ReviewService reviewService;

    private User user;
    private Review review;
    private Comment comment;
    private Contents contents;

    @BeforeEach
    void setUp() {
        user = new User("test@naver.com", "test");
        contents = new Contents(Category.MOVIE, "내용", "작가", "요약", "이미지 링크");
        review = new Review(ReviewRequestDto.builder().title("제목").content("내용").build(), 5, contents, user);
        comment = new Comment("댓글", user, review);
    }

    @Test
    @DisplayName("댓글 생성 성공")
    void createComment_Success() {
        // given
        Long reviewId = 1L;
        CommentRequestDto request = new CommentRequestDto("댓글");
        CommentResponseDto response = CommentResponseDto.builder().id(1L).content("댓글").nickname("test").build();

        when(userService.findUser()).thenReturn(user);
        when(reviewService.findReview(reviewId)).thenReturn(review);
        when(commentConverter.toEntity(request, user, review)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentConverter.toResponse(comment)).thenReturn(response);

        // when
        CommentResponseDto result = commentService.createComment(reviewId, request);

        // Then
        assertNotNull(result);
        assertEquals(response, result);
        verify(commentRepository, times(1)).save(comment);
    }

}

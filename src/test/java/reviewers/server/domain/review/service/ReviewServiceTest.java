package reviewers.server.domain.review.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import reviewers.server.domain.contents.entity.Category;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.repository.ContentsRepository;
import reviewers.server.domain.review.dto.ReviewRequestDto;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.repository.ReviewRepository;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.service.UserService;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    static User user; // 클래스 레벨 필드로 선언
    static Contents contents;

    @Mock
    private UserService userService;

    @Mock
    private ContentsRepository contentsRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User("wjdrhs3473@naver.com", "박정곤");
        contents = Contents.builder()
                .category(Category.BOOK)
                .title("title1")
                .writer("writer1")
                .summary("summary1")
                .build();
    }

    @DisplayName("Review를 생성합니다.")
    @Test
    void createReview() {
     //given
        ReviewRequestDto reviewRequestDto = new ReviewRequestDto("title1-1", "content", 1);

     //when
        when(contentsRepository.findById(1L)).thenReturn(Optional.of(contents));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));

        System.out.println("Before Service Call: contentsRepository = " + contentsRepository);
        System.out.println("Find By ID Result: " + contentsRepository.findById(1L));
        reviewService.create(1L, reviewRequestDto);
        System.out.println("After Service Call");

     //then
        verify(contentsRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }
}
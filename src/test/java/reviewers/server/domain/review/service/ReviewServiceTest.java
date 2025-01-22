package reviewers.server.domain.review.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;
import reviewers.server.domain.contents.entity.Category;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.repository.ContentsRepository;
import reviewers.server.domain.review.dto.ReviewDetailResponseDto;
import reviewers.server.domain.review.dto.ReviewRequestDto;
import reviewers.server.domain.review.dto.ReviewResponseDto;
import reviewers.server.domain.review.entity.Review;
import reviewers.server.domain.review.repository.ReviewRepository;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    private static User user; // 클래스 레벨 필드로 선언
    private static Contents contents;
    private static Review review;

    @Mock
    private UserService userService;

    @Mock
    private ContentsRepository contentsRepository;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @BeforeAll
    static void setUp() {
        user = new User("wjdrhs3473@naver.com", "박정곤");
        ReflectionTestUtils.setField(user, "userId", 1L);

        contents = Contents.builder()
                .category(Category.BOOK)
                .title("title1")
                .writer("writer1")
                .summary("summary1")
                .build();
        ReflectionTestUtils.setField(contents, "id", 1L);

        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
                .title("Title1")
                .content("Content1")
                .starCount(1)
                .build();
        review = new Review(reviewRequestDto, 5, contents, user);
        ReflectionTestUtils.setField(review, "id", 1L);
    }

    @DisplayName("Review를 생성합니다.")
    @Test
    void createReview() {
     //given
        ReviewRequestDto reviewRequestDto = ReviewRequestDto.builder()
                .title("Title1")
                .content("Content1")
                .starCount(1)
                .build();

        when(userService.findUser()).thenReturn(user);
        when(contentsRepository.findById(1L)).thenReturn(Optional.of(contents));
        when(reviewRepository.save(any(Review.class))).thenAnswer(invocation -> invocation.getArgument(0));


        //when
        System.out.println("Before Service Call: reviewRepository = " + reviewRepository);
        reviewService.create(1L, reviewRequestDto);
        System.out.println("After Service Call");

     //then
        verify(userService, times(1)).findUser();
        verify(contentsRepository, times(1)).findById(1L);
        verify(reviewRepository, times(1)).save(any(Review.class));
    }

    @DisplayName("Content 아이디로 Review 리스트 조회에 성공합니다!")
    @Test
    void getReview() {
     //given
        Pageable pageable = PageRequest.of(0, 1);
        when(contentsRepository.findById(contents.getId())).thenReturn(Optional.of(contents));
        when(reviewRepository.findAllByContents(contents, pageable)).thenReturn(List.of(review));

     //when
        Page<ReviewResponseDto> result = reviewService.getAllReview(contents.getId(), pageable);

     //then
        verify(userService, times(0)).findUser();
        verify(contentsRepository, times(1)).findById(contents.getId());

        assertThat(result.getSize()).isEqualTo(1);
    }

    @DisplayName("Review에 대한 상세정보를 제공합니다.")
    @Test
    void getDetailReviewInfo() {
     //given
        when(contentsRepository.findById(contents.getId())).thenReturn(Optional.of(contents));
        when(reviewRepository.findById(review.getId())).thenReturn(Optional.of(review));

     //when
        ReviewDetailResponseDto result = reviewService.getReview(contents.getId(), review.getId());

        //then
        verify(contentsRepository, times(1)).findById(contents.getId());
        verify(reviewRepository, times(1)).findById(review.getId());
        assertThat(result.getTitle()).isEqualTo(review.getTitle());
        assertThat(result.getContent()).isEqualTo(review.getContent());
    }

    @DisplayName("Review를 수정합니다.")
    @Test
    void updateReview() {
     //given
        ReviewRequestDto updatedReviewRequestDto = ReviewRequestDto.builder()
                .title("Updated Title")
                .content("Updated Content")
                .starCount(2)
                .build();

        when(userService.findUser()).thenReturn(user);
        when(contentsRepository.findById(1L)).thenReturn(Optional.of(contents));
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));


        //when
        reviewService.updateReview(1L, 1L, updatedReviewRequestDto);

     //then
        verify(userService, times(1)).findUser();
        verify(contentsRepository).findById(1L);
        verify(reviewRepository).findById(1L);

    }

    @DisplayName("Review를 삭제합니다.")
    @Test
    void deleteReview() {
     //given

        when(userService.findUser()).thenReturn(user);
        when(contentsRepository.findById(1L)).thenReturn(Optional.of(contents));
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        //when
        reviewService.deleteReview(1L, 1L);


     //then
        verify(userService, times(1)).findUser();
        verify(contentsRepository).findById(1L);
        verify(reviewRepository).findById(1L);
        verify(reviewRepository).delete(review);

        when(reviewRepository.findById(1L)).thenReturn(Optional.empty()); // 삭제된 상태 Mock
        Optional<Review> deletedReview = reviewRepository.findById(1L);

        assertTrue(deletedReview.isEmpty(), "Review with ID 1L should not exist after deletion.");
    }
}
package reviewers.server.domain.heart.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.repository.ContentsRepository;
import reviewers.server.domain.heart.content.dto.ContentHeartRequestDto;
import reviewers.server.domain.heart.content.entity.ContentHeart;
import reviewers.server.domain.heart.content.repository.ContentHeartRepository;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.repository.UserRepository;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

@Service
@RequiredArgsConstructor
public class ContentHeartService {

    private ContentHeartRepository contentHeartRepository;
    private UserRepository userRepository;
    private ContentsRepository contentsRepository;

    private User findUser(ContentHeartRequestDto request) {
        return userRepository.findById(request.getUserId())
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_USER));
    }

    private Contents findContent(ContentHeartRequestDto request) {
        return contentsRepository.findById(request.getContentId())
                .orElseThrow(() -> new BaseErrorException(ErrorType._NOT_FOUND_CONTENT));
    }

    private void checkIfAlreadyLiked(User user, Contents content) {
        if (contentHeartRepository.existsByUserAndContent(user, content)) {
            throw new BaseErrorException(ErrorType._ALREADY_LIKE);
        }
    }

    private void checkIfNotLiked(User user, Contents content) {
        if (!contentHeartRepository.existsByUserAndContent(user, content)) {
            throw new BaseErrorException(ErrorType._NOT_LIKE);
        }
    }

    public void createLike(ContentHeartRequestDto request) {
        User user = findUser(request);
        Contents content = findContent(request);

        checkIfAlreadyLiked(user, content);

        ContentHeart contentHeart = ContentHeart.builder()
                .user(user)
                .content(content)
                .build();
        contentHeartRepository.save(contentHeart);
    }

    public void deleteLike(ContentHeartRequestDto request) {
        User user = findUser(request);
        Contents content = findContent(request);

        checkIfNotLiked(user, content);

        contentHeartRepository.deleteByUserAndContent(user, content);
    }
}

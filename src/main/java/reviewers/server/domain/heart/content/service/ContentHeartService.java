package reviewers.server.domain.heart.content.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reviewers.server.domain.contents.entity.Contents;
import reviewers.server.domain.contents.service.ContentsService;
import reviewers.server.domain.heart.content.entity.ContentHeart;
import reviewers.server.domain.heart.content.repository.ContentHeartRepository;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.service.UserService;
import reviewers.server.global.exception.BaseErrorException;
import reviewers.server.global.exception.ErrorType;

@Service
@RequiredArgsConstructor
@Transactional
public class ContentHeartService {

    private final ContentHeartRepository contentHeartRepository;
    private final ContentsService contentsService;
    private final UserService userService;

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

    public void createHeart(Long contentId) {
        User user = userService.findUser();
        Contents content = contentsService.findById(contentId);

        checkIfAlreadyLiked(user, content);

        ContentHeart contentHeart = ContentHeart.builder()
                .user(user)
                .content(content)
                .build();
        contentHeartRepository.save(contentHeart);
        content.addHeartCount();
    }

    public void deleteHeart(Long contentId) {
        User user = userService.findUser();
        Contents content = contentsService.findById(contentId);

        checkIfNotLiked(user, content);

        contentHeartRepository.deleteByUserAndContent(user, content);
        content.subtractHeartCount();
    }
}

package reviewers.server.domain.oauth.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import reviewers.server.domain.oauth.dto.CustomOAuth2User;
import reviewers.server.domain.oauth.dto.GoogleResponse;
import reviewers.server.domain.oauth.dto.OAuth2Response;
import reviewers.server.domain.user.entity.User;
import reviewers.server.domain.user.repository.UserRepository;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class OAuth2UserCustomService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        System.out.println("OAuth2User attributes: " + oAuth2User.getAttributes());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response;

        if (registrationId.equals("google")) {
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else {
            throw new IllegalArgumentException("Unsupported OAuth provider: " + registrationId);
        }


        String username = oAuth2Response.getName(); // 구글 이름 (ex. 노현이)
        String email = oAuth2Response.getEmail();
        String role = "ROLE_USER";

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {  // 처음 로그인하는 경우 (새 회원)
            User user = User.builder()
                    .username(username)
                    .email(email)
                    .role(role)
                    .build();

            System.out.println("처음 로그인합니다.");
            userRepository.save(user);
        } else {  // 이전에 로그인한 경우 (기존 회원)
            User existData = optionalUser.get();
            existData.update(username);
            System.out.println("이전에 로그인했습니다.");
            userRepository.save(existData);
        }

        return new CustomOAuth2User(oAuth2Response, role);
    }
}


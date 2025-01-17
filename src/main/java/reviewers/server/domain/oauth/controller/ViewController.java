package reviewers.server.domain.oauth.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reviewers.server.domain.oauth.dto.CustomOAuth2User;

@RestController
@RequestMapping("/api/v1")
public class ViewController {

    @GetMapping("/loginSuccess")
    public String loginSuccess(@AuthenticationPrincipal CustomOAuth2User customOAuth2User) {
        String accessToken = customOAuth2User.getAccessToken();
        return "Login Success!<br> [Your Google Access Token]<br> " + accessToken;
    }

    @GetMapping("/loginFailed")
    public String loginFailed() {
        return "Login Failed";
    }
}

package reviewers.server.domain.oauth.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reviewers.server.domain.oauth.service.OAuthService;

@RestController
@RequestMapping("/api/v1/user/oauth2")
@RequiredArgsConstructor
public class OAuthController {
    OAuthService oauthService;

    @GetMapping("/code/{registrationId}")
    public void googleLogin(@RequestParam String code, @PathVariable String registrationId) {
        oauthService.socialLogin(code, registrationId);
    }
}

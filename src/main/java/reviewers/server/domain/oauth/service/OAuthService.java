package reviewers.server.domain.oauth.service;

import org.springframework.stereotype.Service;

@Service
public class OAuthService {
    public void socialLogin(String code, String registrationId) {
        System.out.println("code = " + code);
        System.out.println("registrationId = " + registrationId);
    }
}

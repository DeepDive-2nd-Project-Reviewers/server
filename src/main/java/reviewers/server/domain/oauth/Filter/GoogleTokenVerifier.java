package reviewers.server.domain.oauth.Filter;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class GoogleTokenVerifier {

    private static final String GOOGLE_TOKEN_INFO_URL = "https://www.googleapis.com/oauth2/v3/tokeninfo?access_token=";

    public GoogleUserInfo verify(String accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        try {
            String url = GOOGLE_TOKEN_INFO_URL + accessToken;
            Map<String, Object> response = restTemplate.getForObject(url, Map.class);

            if (response == null || response.containsKey("error")) {
                throw new IllegalArgumentException("Invalid Google Access Token");
            }

            // 사용자 정보 반환
            return new GoogleUserInfo(
                    (String) response.get("email"),
                    (String) response.get("name"),
                    (String) response.get("sub")
            );
        } catch (Exception e) {
            throw new IllegalArgumentException("Google Access Token verification failed", e);
        }
    }

    public static class GoogleUserInfo {
        private final String email;
        private final String name;
        private final String providerId;

        public GoogleUserInfo(String email, String name, String providerId) {
            this.email = email;
            this.name = name;
            this.providerId = providerId;
        }

        public String getEmail() {
            return email;
        }

        public String getName() {
            return name;
        }

        public String getProviderId() {
            return providerId;
        }
    }
}


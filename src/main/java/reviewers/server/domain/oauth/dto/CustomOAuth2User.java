package reviewers.server.domain.oauth.dto;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class CustomOAuth2User implements OAuth2User {

    private final OAuth2Response oAuth2Response;
    private final String role;
    private final String accessToken; // 액세스 토큰 추가

    public CustomOAuth2User(OAuth2Response oAuth2Response, String role, String accessToken) {
        this.oAuth2Response = oAuth2Response;
        this.role = role;
        this.accessToken = accessToken;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return oAuth2Response.getAttributes(); // 기존 oAuth2Response의 속성을 반환
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(() -> role); // 람다를 사용해 GrantedAuthority 구현
        return authorities;
    }

    @Override
    public String getName() {
        return oAuth2Response.getName();
    }

    public String getAccessToken() {
        return accessToken; // 액세스 토큰 반환
    }
}


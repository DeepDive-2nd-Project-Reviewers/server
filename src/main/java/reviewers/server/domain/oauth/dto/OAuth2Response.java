package reviewers.server.domain.oauth.dto;

import java.util.Map;

public interface OAuth2Response {

    String getProvider();       // 제공자 이름
    String getProviderId();     // 제공자의 ID
    String getEmail();          // 이메일
    String getName();           // 이름
    Map<String, Object> getAttributes(); // 모든 속성 반환
}

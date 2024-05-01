package cc.gifthub.user.dto;

import java.util.Map;

/*
google response
{resultcode=00, message=success, id=123123123, name=개발자유미, ...}
 */
public class GoogleResponse implements OAuth2Response {

    private final Map<String, Object> attributes;
    public GoogleResponse(final Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String getProvider() {
        return "google";
    }
    // sub: 해당 서비스에서 사용자의 고유 식별자
    @Override
    public String getProviderId() {
        return attributes.get("sub").toString();
    }

    @Override
    public String getEmail() {
        return attributes.get("email").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }
}

package cc.gifthub.user.dto;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.List;
import java.util.Map;
@RequiredArgsConstructor
public class CustomOAuth2User implements OAuth2User {
    private final UserOAuthDto userOAuthDto;

    // oauth 제공 업체마다 규격이 상이하기 때문에 사용 x
    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    // 해당 서비스에서는 사용자의 권한에 따른 특이사항이 없으므로 사용 x
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getName() {
        return userOAuthDto.getName();
    }

    public String getIdentifier(){
        return userOAuthDto.getIdentifier();
    }
}

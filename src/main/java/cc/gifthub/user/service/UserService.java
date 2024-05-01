package cc.gifthub.user.service;

import cc.gifthub.user.dto.UserOAuthDto;
import org.springframework.security.core.context.SecurityContext;

public interface UserService {
    UserOAuthDto getCustomOAuth2User(SecurityContext securityContext);
    Long getCurrentUserId(SecurityContext securityContext);

}

package cc.gifthub.user.service;

import cc.gifthub.user.dto.UserInfoBriefDto;
import cc.gifthub.user.dto.UserInfoDto;
import cc.gifthub.user.dto.UserOAuthDto;
import org.springframework.security.core.context.SecurityContext;

import java.util.List;

public interface UserService {
    Long getCurrentUserId(SecurityContext securityContext);

    List<UserInfoBriefDto> getUserinfoList(Long room_id);
    UserInfoDto getUserinfo(Long user_id);
}

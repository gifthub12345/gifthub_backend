package cc.gifthub.user.service;

import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.dto.CustomOAuth2User;
import cc.gifthub.user.dto.UserOAuthDto;
import cc.gifthub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    @Override
    public UserOAuthDto getCustomOAuth2User(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        CustomOAuth2User customOAuth2User = authentication.getPrincipal() instanceof CustomOAuth2User ? (CustomOAuth2User) authentication.getPrincipal() : null;
        if (customOAuth2User != null) {
            UserEntity userEntity = userRepository.findByIdentifier(customOAuth2User.getIdentifier());
            return UserOAuthDto.toUserOAuthDto(userEntity);
        }
        return null;
    }

    @Override
    public Long getCurrentUserId(SecurityContext securityContext) {
        UserOAuthDto findUserOAuthDto = getCustomOAuth2User(securityContext);

        if (findUserOAuthDto != null) {
            UserEntity findUser = userRepository.findByIdentifier(findUserOAuthDto.getIdentifier());
            return findUser.getId();
        }
        return null;
    }
}

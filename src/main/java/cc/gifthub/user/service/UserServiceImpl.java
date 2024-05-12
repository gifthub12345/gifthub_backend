package cc.gifthub.user.service;

import cc.gifthub.room.domain.RoomEntity;
import cc.gifthub.room.exceotion.RoomNotFoundException;
import cc.gifthub.user.dto.UserInfoBriefDto;
import cc.gifthub.user.dto.UserInfoDto;
import cc.gifthub.room.repository.RoomRepository;
import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.dto.CustomOAuth2User;
import cc.gifthub.user.dto.UserOAuthDto;
import cc.gifthub.user.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Override
    public Long getCurrentUserId(SecurityContext securityContext) {
        Authentication authentication = securityContext.getAuthentication();
        CustomOAuth2User customOAuth2User = authentication.getPrincipal() instanceof CustomOAuth2User ? (CustomOAuth2User) authentication.getPrincipal() : null;
        if (customOAuth2User != null) {
            UserEntity userEntity = userRepository.findByIdentifier(customOAuth2User.getIdentifier());
            UserOAuthDto findUserOAuthDto = UserOAuthDto.toUserOAuthDto(userEntity);
            if (findUserOAuthDto != null) {
                UserEntity findUser = userRepository.findByIdentifier(findUserOAuthDto.getIdentifier());
                return findUser.getId();
            }

        }

        return null;
    }
    @Override
    public List<UserInfoBriefDto> getUserinfoList(Long room_id) {
        Optional<RoomEntity> roomById = roomRepository.findById(room_id);
        if(roomById.isPresent()){
            List<UserInfoBriefDto> userInfoBriefDtoList = new ArrayList<>();
            RoomEntity roomEntity = roomById.get();
            for(UserEntity userEntity : roomEntity.getUsers()){
                UserInfoBriefDto userInfoBriefDto = UserInfoBriefDto.builder()
                        .name(userEntity.getName())
                        .build();
                userInfoBriefDtoList.add(userInfoBriefDto);
            }
            return userInfoBriefDtoList;
        }else{
            throw new RoomNotFoundException();
        }
    }

    @Override
    public UserInfoDto getUserinfo(Long user_id) {
        Optional<UserEntity> userById = userRepository.findById(user_id);
        if(userById.isPresent()){
            UserEntity userEntity = userById.get();
            UserInfoDto userInfoDto = UserInfoDto.builder()
                    .name(userEntity.getName())
                    .email(userEntity.getEmail())
                    .build();
            return userInfoDto;
        }
        return null;
    }
}

package cc.gifthub.user.service;

import cc.gifthub.user.domain.UserEntity;
import cc.gifthub.user.dto.*;
import cc.gifthub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("oAuth2User: {}", oAuth2User.getName());

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // oauth 제공 업체마다 규격이 상이하기 때문에 null -> 특정 response 로 캐스팅
        OAuth2Response oAuth2Response = null;
        if(registrationId.equals("google")){
            oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
        } else if (registrationId.equals("naver")) {
            oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
        }
//        else if(registrationId.equals("apple")){
//            apple login 은 추후 개발
//        }
        else{
            return null;
        }

        // 제공 업체로부터 사용자의 name과 providerId를 조합해 식별자 생성 -> 중복된 이름에 대해 식별 가능
        String identifier = oAuth2Response.getName() + oAuth2Response.getProviderId();

        // 식별자를 기반으로 db에 존재하는 회원인지 검증
        UserEntity existUser = userRepository.findByIdentifier(identifier);

        // 첫 로그인 -> 사용자 정보 db에 저장!
        if(existUser == null){
            log.info("{} - 최초 로그인", oAuth2Response.getName());

            UserEntity newUser = UserEntity.builder()
                    .identifier(identifier)
                    .name(oAuth2Response.getName())
                    .email(oAuth2Response.getEmail())
                    .build();
            userRepository.save(newUser);

            UserOAuthDto userOAuthDto = UserOAuthDto.builder()
                    .identifier(identifier)
                    .name(oAuth2Response.getName())
                    .build();
            return new CustomOAuth2User(userOAuthDto);
        }
        // 이전에 로그인 -> db에 남아있는 정보와 비교 후  정보 갱신 및 로그인 진행
        else{
            log.info("{} - 최초 로그인 x", oAuth2Response.getName());

            existUser.updateNameAndEmail(oAuth2Response.getName(), oAuth2Response.getEmail());
            userRepository.save(existUser);

            UserOAuthDto userOAuthDto = UserOAuthDto.builder()
                    .identifier(identifier)
                    .name(existUser.getName())
                    .build();
            return new CustomOAuth2User(userOAuthDto);
        }

    }
}

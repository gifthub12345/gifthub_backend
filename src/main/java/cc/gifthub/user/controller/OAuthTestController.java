package cc.gifthub.user.controller;

import cc.gifthub.user.dto.UserOAuthDto;
import cc.gifthub.user.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Slf4j
@RequiredArgsConstructor
public class OAuthTestController {
    private final UserServiceImpl userServiceImpl;

    @GetMapping("/v1")
    public String mainAPI1() {
        UserOAuthDto userOAuthDto =  userServiceImpl.getCustomOAuth2User(SecurityContextHolder.getContext());

        return "v1 user name: " + userOAuthDto.getName();
    }

    @GetMapping("/v2")
    public String mainAPI2() {
        UserOAuthDto userOAuthDto = userServiceImpl.getCustomOAuth2User(SecurityContextHolder.getContext());

        return "v2 user name: " + userOAuthDto.getName();
    }




}

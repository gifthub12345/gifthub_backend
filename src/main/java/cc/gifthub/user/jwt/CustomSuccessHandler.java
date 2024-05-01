package cc.gifthub.user.jwt;

import cc.gifthub.user.dto.CustomOAuth2User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JWTUtil jwtUtil;

    /*
    OAuth2 로그인이 성공하면 커스텀한 success handler 가 실행됨
    -> jwt token 진행 [back -> front]
    이 때 jwt는 STATELESS 방식을 지향하는데 왜 쿠키를 사용하나요?
    무상태성을 지향하는 jwt에서도 인증인가의 방식에서는 쿠키 및 세션 방식을 통해 동작할 수 있음
    이 때 쿠키 및 세션의 지속시간을 짧게 하고 인증 및 인가를 시도할 떄마다 쿠키나 세션을 발급하고 종료시키는 방식을 사용함!
    다만 위 프로젝트에서는 단일 토큰 방식 사용함! -> 사실상 jwt의 stateless특성 사용 x
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("oauth2 login success");

        CustomOAuth2User customOAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String identifier = customOAuth2User.getIdentifier();

        // token 생성
        String token = jwtUtil.createJwt(identifier, 60*60*60l);

        log.info("jwt token 발급 - {}", token);
        log.info("token identifier - {}", identifier);
        response.addCookie(createCookie("Authorization", token));

        /*
        백엔드 서버에서는 인증이 완료된 이후에 jwt token 을 생성하고 이를 프론트로 전달한다.
        이 때 보안상의 이유로 http 헤더에 직접 토큰을 포함하지 않고, token을 쿠키에 저장하고 프론트의 3000번 포트로 리디렉션한다.
        그러면 토큰을 담은 쿠키를 프론트에서 사용하여 인증된 사용자임을 확인하고 필요한 요청에 jwt 토큰을 포함시켜 백엔드에 전달하게 된다.
         */
        response.sendRedirect("http://localhost:3000/");
        log.info("token을 cookie에 담아 프론트로 전송 완료!");
    }


    private Cookie createCookie(String key, String value){
        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60*365);
        // cookie.setSecure(true) // https 에서만!
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        return cookie;
    }
}

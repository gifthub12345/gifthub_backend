package cc.gifthub.user.jwt;

import cc.gifthub.user.dto.CustomOAuth2User;
import cc.gifthub.user.dto.UserOAuthDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    /*
    CustomSuccessHandler는 토큰을 발급하고 그 토큰을 쿠키에 담아 프론트에 전송하는 과정이었다.
    JWTFilter는 이러한 토큰을 프론트가 백으로 넘겨줄 때에 대해서 동작한다.
    클라이언트가 특정 페이지에 대해 토큰과 함께 요청을 전송하면 토큰의 유효성을 검증힌다.
    유효한 경우 이 프로젝트에서는 oauth2를 사용하기 때문에 시큐리티 컨텍스트에 사용자 정보(CustomOAuth2User)를 저장한다.
    그리고 사용자가 그 페이지를 사용하는 동안은 시큐리티 컨텍스트에 저장된 세션을 통해 인증된다.
    OncePerRequestFilter를 상속했기 때문에 페이지를 나가거나 다른 동작을 수행하면 기존의 시큐리티 컨텍스트에 저장된 정보는 삭제된다.
     */

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // ["/login", "/oauth2"] 같은 로그인 관련 요청에 대해서는 필터링 적용x -> 토큰 만료, 재로그인 과정에서 무한 루프 발생 방지
        String requestURI = request.getRequestURI();

        if (requestURI.matches("^\\/login(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }
        if (requestURI.matches("^\\/oauth2(?:\\/.*)?$")) {
            filterChain.doFilter(request, response);
            return;
        }

        //cookie들을 불러온 뒤 Authorization Key에 담긴 쿠키를 찾음
        String authorization = null;
        Cookie[] cookies = request.getCookies();

        for (Cookie cookie : cookies) {
            if (cookie.getName().equals("Authorization")) {
                authorization = cookie.getValue();
            }
        }
        //검증1. Authorization 헤더 검증
        if (authorization == null) {

            System.out.println("token null");
            filterChain.doFilter(request, response);

            //조건이 해당되면 메소드 종료 (필수)
            return;
        }
        //토큰
        String token = authorization;

        //검증2. 토큰이 만료된 경우
        if (jwtUtil.isTokenExpired(token)){
            log.info("token expired");
            filterChain.doFilter(request, response);
            return;
        }
        String identifier = jwtUtil.getIdentifierFromToken(token);

        UserOAuthDto userOAuthDto = UserOAuthDto.builder()
                .identifier(identifier)
                .build();

        // 토큰에서 사용자 정보를 추출하여 인증용 객체 생성
        CustomOAuth2User customOAuth2User = new CustomOAuth2User(userOAuthDto);

        // 인증용 객체를 바탕으로 context holder에 등록하기 위한 토큰 생성
        /*
        2번째 파라메터인 credentials은 사용자의 자격증명을 나타내며 일반적으로는 사용자의 암호를 의미한다.
        토큰 방식에서는 사용자의 암호를 토큰에 포함시키지 않는다. 엄밀히 하자면 사용자의 암호를 서버에 보관하거나 전송하지 않고, 토큰을 통해 인증을 수행한다.
        3번째 파라메터인 authorities는 이 서비스에서는 필요하지 않기 때문에 사용 x
         */
        Authentication authToken = new UsernamePasswordAuthenticationToken(customOAuth2User, null, null);

        // 세션에 사용자 등록 -> 사용자가 요청한 페이지를 사용하는 동안은 이 세션을 바탕으로 인증
        SecurityContextHolder.getContext().setAuthentication(authToken);
        filterChain.doFilter(request, response);

    }
}

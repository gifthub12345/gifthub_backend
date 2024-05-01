package cc.gifthub.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/*
전역적인 cors 설정을 통해 spring mvc를 담당
Spring Mvc는 IoC(inversion of control)로 인해서 전역적인 설정을 하지 않으면 충돌이 발생할 수 있다.
이를 위해 security config 외에도 추가적으로 전역설정을 해주어야 한다.
또한 인증을 제외하고는 Authorization 를 노출하지 않음으로써 브라우저에서 보안성을 높일 수 있다.
 */
@Configuration
public class CorsMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {

        corsRegistry.addMapping("/**")
                .exposedHeaders("Set-Cookie")
                .allowedOrigins("http://localhost:3000");
    }
}
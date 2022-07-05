package shop.geeksasang.utils.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import shop.geeksasang.interceptor.AuthenticationInterceptor;


@Configuration
@Slf4j
@RequiredArgsConstructor
public class AppConfig implements WebMvcConfigurer {
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        //인터셉터 등록 -> "/login","/members"는 인터셉터 제외
        registry.addInterceptor(new AuthenticationInterceptor(jwtService,objectMapper))
                .order(1)
                .addPathPatterns("/**");
    }

}

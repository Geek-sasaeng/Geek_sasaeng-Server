package shop.geeksasang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${request.url}")
    private String host;
    private final String localhost = "localhost:8080"; // 로컬 호스트 테스트용

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .securityContexts(Arrays.asList(securityContext()))
                .securitySchemes(Arrays.asList(apiKey()))
                .host(localhost)
                .apiInfo(getApiInfo())
                .useDefaultResponseMessages(false);
    }
    private ApiInfo getApiInfo() {
        return new ApiInfoBuilder()
                .title("[Geek-sasaeng] REST API")
                .description("긱사생 API 명세서 입니다.\n" +
                        "2022.07.04 ~ 2022.07.10 로그인 ~ 회원가입 API 구현 완료")
                .contact(new Contact("긱사생 팀", "https://geeksa.notion.site/3de47b97eeb141c19af75e2373a425a5", "geeksasaeng@gmail.com" ))
                .version("1.0")
                .build();
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
    }

    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header"); //이건 Bearer 방식은 아닌 듯. 임의로 지정
    }
}

/**
 * SecurityContext
 * Authentication 객체가 저장되는 보관소로 필요 시 언제든지 Authentication 객체를 꺼내어 쓸 수 있도록 제공되는 클래스
 * ThreadLocal에 저장되어 아무 곳에서나 참조가 가능하도록 설계함. 결국 쓰레드 1개에서만 접근 가능하다.
 * 인증이 완료되면 HttpSession에 저장되어 어플리케이션 전반에 걸쳐 전역적인 참조가 가능하다.
 */
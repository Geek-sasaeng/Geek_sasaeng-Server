package shop.geeksasang.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Value("${request.url}")
    private String host;

    @Bean
    public Docket api(){
        return new Docket(DocumentationType.SWAGGER_2)
                .host(host)
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

}

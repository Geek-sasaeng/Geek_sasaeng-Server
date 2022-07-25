package shop.geeksasang.utils.resttemplate.naverlogin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import shop.geeksasang.config.exception.BaseException;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.NAVER_LOGIN_ERROR;

@RequiredArgsConstructor
@Service
public class NaverLoginRequest {
    private final RestTemplate restTemplate;
    private final String BaseURL = "https://openapi.naver.com/v1/nid/me";
    // 네이버 사용자 토큰 받아오기
    String phoneNumber = null;
    String loginId = null;
    public NaverLoginData getToken(String accessToken) {
        NaverLoginData data = null;
        try {
            // Header - access Token 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("Authorization", "Bearer "+ accessToken);
            HttpEntity<String> entity = new HttpEntity<>(headers);

            // Get 요청 후 Response 저장
            ResponseEntity<NaverLoginResponse> response = restTemplate.exchange(BaseURL, HttpMethod.GET, entity, NaverLoginResponse.class);
            data = response.getBody().getResponse();
            phoneNumber = data.getMobile();
            loginId = data.getEmail();
        } catch (HttpStatusCodeException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new BaseException(NAVER_LOGIN_ERROR);
            }
        }
        if (phoneNumber == null || loginId == null)
            throw new BaseException(NAVER_LOGIN_ERROR);
        return data;
    }
}
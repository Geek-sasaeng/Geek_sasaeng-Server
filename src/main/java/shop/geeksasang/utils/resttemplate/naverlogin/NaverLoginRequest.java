package shop.geeksasang.utils.resttemplate.naverlogin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import shop.geeksasang.config.exception.BaseException;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.NAVER_LOGIN_ERROR;

@RequiredArgsConstructor
@Service
public class NaverLoginRequest {
    private final RestTemplate restTemplate;
    // 네이버 사용자 토큰 받아오기
    String phoneNumber = null;
    String loginId = null;
    public NaverLoginData getToken(String loginURL) {
        NaverLoginData data = null;
        try {
            NaverLoginResponse response = restTemplate.getForObject(loginURL, NaverLoginResponse.class);
            phoneNumber = data.getPhoneNumber();
            loginId = data.getEmail();
            data = response.getResponse();
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
package shop.geeksasang.service;

import lombok.Data;

import java.io.Serializable;

// 네이버 로그인 토큰 요청 시 Response Data
@Data
public class NaverLoginResponse implements Serializable {
    private String resultcode;
    private String message;
    private NaverLoginData response;
}
@Data
class NaverLoginData {
    private String email;
    private String phoneNumber;
}

package shop.geeksasang.controller.applelogin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppleLoginReq {

    private ServicesResponse servicesResponse;

    private String refreshToken; // 로그인 처리를 위한 token
}

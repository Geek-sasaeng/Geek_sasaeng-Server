package shop.geeksasang.controller.applelogin.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AppleLoginReq {

    @NotEmpty
    private String idToken; // identity_token

    @NotEmpty
    private String refreshToken; // 로그인 처리를 위한 token
}

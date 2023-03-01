package shop.geeksasang.controller.applelogin.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.controller.applelogin.model.UserObject;

@Getter
@NoArgsConstructor
public class AppleSignUpReq {

    private String code;
    private String idToken;

    public AppleSignUpReq(String code, String idToken) {
        this.code = code;
        this.idToken = idToken;
    }
}

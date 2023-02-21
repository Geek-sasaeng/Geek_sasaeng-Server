package shop.geeksasang.controller.applelogin.controller;

import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.controller.applelogin.model.UserObject;

@Getter
@NoArgsConstructor
public class AppleSignUpReq {

    private String code;
    private String idToken;
    private UserObject user;

    public AppleSignUpReq(String code, String idToken, UserObject user) {
        this.code = code;
        this.idToken = idToken;
        this.user = user;
    }
}

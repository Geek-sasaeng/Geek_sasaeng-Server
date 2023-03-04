package shop.geeksasang.controller.applelogin.controller;

import lombok.Getter;

@Getter
public class TempDto {

    private String jwt;
    private String nickName;

    public TempDto(String jwt, String nickName) {
        this.jwt = jwt;
        this.nickName = nickName;
    }
}

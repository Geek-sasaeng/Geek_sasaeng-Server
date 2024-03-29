package shop.geeksasang.controller.applelogin.model;


import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServicesResponse {

    private String state;
    private String code; // authorization_token
    private String id_token; // identity_token
    private UserObject user; // 애플에서 제공하는 유저 정보
    private String identifier;
    private Boolean hasRequirementInfo; // 유저 필수정보 입력 여부

}

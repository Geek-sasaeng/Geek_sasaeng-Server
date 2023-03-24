package shop.geeksasang.controller.applelogin.model;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.domain.auth.Email;
import shop.geeksasang.domain.auth.PhoneNumber;
import shop.geeksasang.domain.member.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor
public class AppleSignUpReq {

    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "사용자 닉네임", required = true)
    //@Size(min = 3, max = 10)
    private String nickname;

    @ApiModelProperty(example = "Gachon University")
    @ApiParam(value = "사용자 대학교", required = true)
    //@NotBlank
    private  String universityName;

    @ApiModelProperty(example = "01012341234")
    @ApiParam(value = "사용자 폰 번호", required = true)
    private String phoneNumber;

    @ApiModelProperty(example = "forceTlight@gachon.ac.kr")
    @ApiParam(value = "사용자 학교 이메일", required = true)
    private String email;

    @ApiModelProperty(example = "Y")
    @ApiParam(value = "사용자 회원 정보 동의 여부", required = true)
    //@NotBlank(message = "회원정보동의는 Y 를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String informationAgreeStatus;

    @NotEmpty
    private String code;

    @NotEmpty
    private String idToken;

    public Member toEntity(Email email, PhoneNumber phoneNumber, String nickName, String refreshToken) {
        return Member.builder()
                .appleRefreshToken(refreshToken)
                .nickName(nickName)
                .email(email)
                .phoneNumber(phoneNumber)
                .informationAgreeStatus(informationAgreeStatus)
                .memberLoginType(MemberLoginType.APPLE_USER)
                .appleRefreshToken(refreshToken)
                .build();
    }
}

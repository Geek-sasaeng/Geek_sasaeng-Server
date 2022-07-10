package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import shop.geeksasang.domain.Member;

public class PostSocialRegisterRes {

    @ApiModelProperty(example = "geeksasaeng@gmail.com")
    private  String loginId;

    @ApiModelProperty(example = "긱사생")
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    private  String universityName;

    @ApiModelProperty(example = "abc@gachon.ac.kr")
    private  String email;

    @ApiModelProperty(example = "01012341234")
    private  String phoneNumber;

    static public PostRegisterRes toDto(Member member) {
        return PostRegisterRes.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityName(member.getUniversity().getName())
                .email(member.getEmail())
                .phoneNumber(member.getPhoneNumber())
                .build();
    }
}

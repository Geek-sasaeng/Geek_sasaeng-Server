package shop.geeksasang.dto.member.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Email;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.PhoneNumber;

@Getter @Setter
@Builder
public class PostRegisterRes {

    @ApiModelProperty(example = "geeksasaeng")
    @ApiParam(value = "사용자 ID")
    private  String loginId;

    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "사용자 닉네임")
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    @ApiParam(value = "사용자 대학교")
    private  String universityName;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 이메일")
    private int email;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "사용자 핸드폰 번호")
    private int phoneNumber;

    static public PostRegisterRes toDto(Member member, Email email, PhoneNumber phoneNumber) {
        return PostRegisterRes.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityName(member.getUniversity().getName())
                .email(email.getId())
                .phoneNumber(phoneNumber.getId())
                .build();
    }
}

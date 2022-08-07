package shop.geeksasang.dto.member.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.Email;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.PhoneNumber;

@Getter
@Setter
@Builder
public class PostSocialRegisterRes {

    @ApiModelProperty(example = "geeksasaeng@gmail.com")
    private  String loginId;

    @ApiModelProperty(example = "긱사생")
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    private  String universityName;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "사용자 이메일 인덱스")
    private int email;

    @ApiModelProperty(example = "3")
    @ApiParam(value = "사용자 핸드폰 번호 인덱스")
    private int phoneNumber;

    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqd3RJbmZvIjp7InVuaXZlcnNpdHlJZCI6MSwidXNlcklkIjoxN30sImlhdCI6MTY1NzQ1MTU1NiwiZXhwIjoxNjU4MzQwNTg5fQ.0H1fUvms49VVcH9gkKD5P3PVP8X73mfX_r8Y14qH598")
    @ApiParam(value = "jwt Token")
    private String jwt;

    static public PostSocialRegisterRes toDto(Member member, Email email, PhoneNumber phoneNumber, String jwt) {
        return PostSocialRegisterRes.builder()
                .loginId(member.getLoginId())
                .nickname(member.getNickName())
                .universityName(member.getUniversity().getName())
                .email(email.getId())
                .phoneNumber(phoneNumber.getId())
                .jwt(jwt)
                .build();
    }
}

package shop.geeksasang.dto.member.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.type.MemberLoginType;
import shop.geeksasang.domain.auth.Email;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.auth.PhoneNumber;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSocialRegisterReq {
    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "사용자 닉네임", required = true)
    @Size(min = 3, max = 10)
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    @ApiParam(value = "사용자 대학교", required = true)
    @NotBlank
    private  String universityName;

    @ApiModelProperty(example = "forceTlight@gachon.ac.kr")
    @ApiParam(value = "사용자 학교 이메일", required = true)
    private String email;

    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqd3RJbmZvIjp7InVuaXZlcnNpdHlJZCI6MSwidXNlcklkIjoxN30sImlhdCI6MTY1NzQ1MTU1NiwiZXhwIjoxNjU4MzQwNTg5fQ.0H1fUvms49VVcH9gkKD5P3PVP8X73mfX_r8Y14qH598")
    private String accessToken;

    @ApiModelProperty(example = "Y")
    @ApiParam(value = "사용자 회원 정보 동의 여부", required = true)
    @NotBlank(message = "회원정보동의는 Y 를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String informationAgreeStatus;

    public Member toEntity(Email email, PhoneNumber phoneNumber, String loginId, String password) {
        return Member.builder()
                .loginId(loginId)
                .password(password)
                .nickName(getNickname())
                .email(email)
                .phoneNumber(phoneNumber)
                .informationAgreeStatus(getInformationAgreeStatus())
                .memberLoginType(MemberLoginType.NAVER_USER)
                .build();
    }
}

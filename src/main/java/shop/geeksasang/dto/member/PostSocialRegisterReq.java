package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.config.domain.MemberLoginType;
import shop.geeksasang.domain.Member;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostSocialRegisterReq {
    @ApiModelProperty(example = "geeksasaeng@naver.com")
    @ApiParam(value = "사용자 ID", required = true)
    @Pattern(regexp="^[a-zA-Z0-9+-\\_.]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+$",
            message = "아이디는 이메일 형식만 지원됩니다.")
    private  String loginId;

    @ApiModelProperty(example = "1q2w3e4r!")
    @ApiParam(value = "사용자 비밀번호", required = true)
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String password;

    @ApiModelProperty(example = "1q2w3e4r!")
    @ApiParam(value = "사용자 비밀번호 체크", required = true)
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String checkPassword;

    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "사용자 닉네임", required = true)
    @Size(min = 3, max = 10)
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    @ApiParam(value = "사용자 대학교", required = true)
    @NotBlank
    private  String universityName;

    @ApiModelProperty(example = "abc@gachon.ac.kr")
    @ApiParam(value = "사용자 학교 이메일", required = true)
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.ac.kr$")
    @Email
    private  String email;

    @ApiModelProperty(example = "01012341234")
    @ApiParam(value = "사용자 핸드폰 번호", required = true)
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$")

    private  String phoneNumber;
    @ApiModelProperty(example = "Y")
    @ApiParam(value = "사용자 회원 정보 동의 여부", required = true)
    @NotBlank(message = "회원정보동의는 Y 를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String informationAgreeStatus;

    public Member toEntity() {
        return Member.builder()
                .loginId(getLoginId())
                .password(getPassword())
                .nickName(getNickname())
                .email(getEmail())
                .phoneNumber(getPhoneNumber())
                .informationAgreeStatus(getInformationAgreeStatus())
                .memberLoginType(MemberLoginType.NAVER_USER)
                .build();
    }

}
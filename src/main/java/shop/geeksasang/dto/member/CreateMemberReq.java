package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.hibernate.validator.constraints.Range;
import shop.geeksasang.domain.Member;

import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateMemberReq {
    @ApiModelProperty(example = "geeksasaeng")
    @Size(min = 6, max = 20)// validation: 최소길이 6자
    @Pattern(regexp="^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{6,20}$",
            message = "아이디는 6-20자의 영문과 숫자, 일부 특수문자(._-)만 입력 가능합니다.")
    private  String loginId;

    @ApiModelProperty(example = "1q2w3e4r!")
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String password;

    @ApiModelProperty(example = "1q2w3e4r!")
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String checkPassword;

    @ApiModelProperty(example = "긱사생")
    @Size(min = 3, max = 10)
    private  String nickname;

    @ApiModelProperty(example = "Gachon University")
    @NotBlank
    private  String universityName;

    @ApiModelProperty(example = "abc@gachon.ac.kr")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.ac.kr$")
    @Email
    private  String email;

    @ApiModelProperty(example = "01012341234")
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\\\d{3}|\\\\d{4})\\\\d{4}$")
    private  String phoneNumber;

    @ApiModelProperty(example = "Y")
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
                .build();
    }
}









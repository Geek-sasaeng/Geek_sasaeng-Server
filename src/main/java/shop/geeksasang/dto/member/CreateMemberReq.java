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
    @ApiModelProperty(value = "로그인 아이디, 최소 6자")
    @Size(min = 6, max = 20)
    @Pattern(regexp="^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{6,20}$",
            message = "아이디는 6-20자의 영문과 숫자, 일부 특수문자(._-)만 입력 가능합니다.")
    private  String loginId;

    @ApiModelProperty(value = "비밀번호, 최소 8자")
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String password;

    @ApiModelProperty(value = "비밀번호 확인용, 최소 8자")
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String checkPassword;

    @ApiModelProperty(value = "닉네임, 최소 5자")
    @Size(min = 5, max = 10)
    private  String nickname;

    @ApiModelProperty(value = "대학 이름")
    @NotBlank
    private  String universityName;

    @ApiModelProperty(value = "이메일을 사용")
    @NotBlank(message = "이메일을 입력해주세요.")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.ac.kr$")
    @Email
    private  String email;

    @ApiModelProperty(value = "휴대폰 번호만 입력. 최소 입력 10, 최대 11")
    @Size(min = 10, max = 11)
    @Pattern(regexp = "^\\d{2,3}\\d{3,4}\\d{4}$")
    private  String phoneNumber;

    public Member toEntity() {
        return Member.builder()
                .loginId(getLoginId())
                .password(getPassword())
                .nickName(getNickname())
                .email(getEmail())
                .phoneNumber(getPhoneNumber())
                .build();
    }
}









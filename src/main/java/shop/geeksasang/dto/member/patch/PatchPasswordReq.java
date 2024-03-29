package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
public class PatchPasswordReq {

    @ApiModelProperty(example = "rlrtktod12!", value = "사용자 새로운 비밀번호", required = true)
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private String newPassword;

    @ApiModelProperty(example = "rlrtktod12!", value = "사용자 새로운 비밀번호 체크", required = true)
    @Size(min = 8, max = 15)
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private String checkNewPassword;
}

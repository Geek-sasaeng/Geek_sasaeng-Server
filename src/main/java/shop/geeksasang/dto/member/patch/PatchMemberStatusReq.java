package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class PatchMemberStatusReq {

    @ApiModelProperty(example = "1q2w3e4r!")
    @ApiParam(value = "사용자 비밀번호", required = true)
    @Size(min = 8, max = 15,message = "최소 8자, 최대 15자")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String password;

    @ApiModelProperty(example = "1q2w3e4r!")
    @ApiParam(value = "사용자 비밀번호 체크", required = true)
    @Size(min = 8, max = 15,message = "최소 8자, 최대 15자")
    @Pattern(regexp="^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,15}$",
            message = "비밀번호는 최소 8 자로 문자, 숫자 및 특수 문자를 최소 하나씩 포함해서 8-15자리 이내로 입력해주세요.")
    private  String checkPassword;
}

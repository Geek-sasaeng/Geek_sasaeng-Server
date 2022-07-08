package shop.geeksasang.dto.login;

import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class LoginReq {
    @ApiModelProperty(value="로그인 아이디, 최소 6자 이상")
    @Size(min = 6)
    private String loginId;

    @ApiModelProperty(value="비밀번호, 최소 8자 이상")
    @Size(min = 8)
    private String password;
}

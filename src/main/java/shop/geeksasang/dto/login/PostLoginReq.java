package shop.geeksasang.dto.login;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostLoginReq {
    @ApiModelProperty(value="로그인 아이디, 최소 6자 이상 20자 이하")
    @ApiParam(value = "사용자 ID", required = true)
    @Size(min = 6, max = 20)
    private String loginId;

    @ApiModelProperty(value="비밀번호, 최소 8자 이상")
    @ApiParam(value = "사용자 비밀번호", required = true)
    @Size(min = 8)
    private String password;
}

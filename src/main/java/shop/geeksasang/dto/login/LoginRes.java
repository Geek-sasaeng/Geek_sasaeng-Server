package shop.geeksasang.dto.login;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.LoginStatus;

import javax.validation.constraints.Size;


@Getter @Setter
@Builder
public class LoginRes {
    private String jwt;

    @ApiModelProperty(example = "FIRST")
    @ApiParam(value = "로그인 횟수 상태")
    private LoginStatus loginStatus;
}

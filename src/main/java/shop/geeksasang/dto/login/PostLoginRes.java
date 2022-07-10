package shop.geeksasang.dto.login;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.LoginStatus;


@Getter @Setter
@Builder
public class PostLoginRes {

    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqd3RJbmZvIjp7InVuaXZlcnNpdHlJZCI6MSwidXNlcklkIjoxN30sImlhdCI6MTY1NzQ1MTU1NiwiZXhwIjoxNjU4MzQwNTg5fQ.0H1fUvms49VVcH9gkKD5P3PVP8X73mfX_r8Y14qH598")
    @ApiParam(value = "jwt Token")
    private String jwt;

    @ApiModelProperty(example = "NEVER")
    @ApiParam(value = "로그인 횟수 상태")
    private LoginStatus loginStatus;
}

package shop.geeksasang.dto.login;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostSocialLoginReq {
    @ApiModelProperty(example = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&code=EIc5bFrl4RibFls1&state=9kgsGTfH4j7IyAkg")
    @ApiParam(value = "네이버 로그인 요청 URL", required = true)
    private String SocialLoginURL;
}

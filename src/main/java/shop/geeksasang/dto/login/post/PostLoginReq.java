package shop.geeksasang.dto.login.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PostLoginReq {
    @ApiModelProperty(value="로그인 아이디, 최소 6자 이상",example = "xhaktmchl15")
    @ApiParam(value = "사용자 ID", required = true)
    @NotBlank(message = "아이디를 입력하세요")
    private String loginId;

    @ApiModelProperty(value="비밀번호, 최소 8자 이상",example = "chlxorb!2")
    @ApiParam(value = "사용자 비밀번호", required = true)
    @NotBlank(message = "비밀번호를 입력하세요")
    private String password;

    @ApiModelProperty(example = "c1aTnbZPaWP0_KZbcSuh_h:APA91bFG4r3Z6-dw88UQFikVxkNApaUeq508RNeYxgmrj7miyGdSIy6YXMLJj3jAmCZERNqSLgLm4gn3fyhnBn2iUsra_EriXMqsOlXkyUf9ugNAKMPiKZGNYbn6gKLhMYWB3xTJoRVk")
    @ApiParam(value = "FCM 토큰값", required = true)
    @NotBlank
    private String fcmToken;
}

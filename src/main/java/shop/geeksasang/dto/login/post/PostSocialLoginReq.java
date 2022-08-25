package shop.geeksasang.dto.login.post;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostSocialLoginReq {
    @ApiModelProperty(example = "eyJ0eXBlIjoiand0IiwiYWxnIjoiSFMyNTYifQ.eyJqd3RJbmZvIjp7InVuaXZlcnNpdHlJZCI6MSwidXNlcklkIjoxN30sImlhdCI6MTY1NzQ1MTU1NiwiZXhwIjoxNjU4MzQwNTg5fQ.0H1fUvms49VVcH9gkKD5P3PVP8X73mfX_r8Y14qH598")
    @ApiParam(value = "네이버 로그인 ACCESS TOKEN", required = true)
    private String accessToken;

    @ApiModelProperty(example = "c1aTnbZPaWP0_KZbcSuh_h:APA91bFG4r3Z6-dw88UQFikVxkNApaUeq508RNeYxgmrj7miyGdSIy6YXMLJj3jAmCZERNqSLgLm4gn3fyhnBn2iUsra_EriXMqsOlXkyUf9ugNAKMPiKZGNYbn6gKLhMYWB3xTJoRVk")
    @ApiParam(value = "FCM 토큰값", required = true)
    @NotBlank
    private String fcmToken;
}

package shop.geeksasang.controller.applelogin.model;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class AppleLoginReq {

    @NotEmpty
    private String idToken; // identity_token

    @NotEmpty
    private String refreshToken; // 로그인 처리를 위한 token

    @ApiModelProperty(example = "c1aTnbZPaWP0_KZbcSuh_h:APA91bFG4r3Z6-dw88UQFikVxkNApaUeq508RNeYxgmrj7miyGdSIy6YXMLJj3jAmCZERNqSLgLm4gn3fyhnBn2iUsra_EriXMqsOlXkyUf9ugNAKMPiKZGNYbn6gKLhMYWB3xTJoRVk")
    @ApiParam(value = "FCM 토큰값")
    @NotEmpty
    private String fcmToken;
}

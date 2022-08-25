package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PatchFcmTokenReq {

    @ApiModelProperty(example = "c1aTnbZPaWP0_KZbcSuh_h:APA91bFG4r3Z6-dw88UQFikVxkNApaUeq508RNeYxgmrj7miyGdSIy6YXMLJj3jAmCZERNqSLgLm4gn3fyhnBn2iUsra_EriXMqsOlXkyUf9ugNAKMPiKZGNYbn6gKLhMYWB3xTJoRVk")
    @ApiParam(value = "FCM 토큰값", required = true)
    private String fcmToken;
}

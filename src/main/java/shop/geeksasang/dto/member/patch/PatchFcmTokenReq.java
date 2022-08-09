package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PatchFcmTokenReq {

    @ApiModelProperty(example = "토큰예시 발급받으면 입력할 예정")
    @ApiParam(value = "FCM 토큰값", required = true)
    private String FcmToken;
}

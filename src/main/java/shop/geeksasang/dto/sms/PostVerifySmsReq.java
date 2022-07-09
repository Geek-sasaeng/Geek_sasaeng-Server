package shop.geeksasang.dto.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PostVerifySmsReq {
    @ApiModelProperty(value = "SMS 인증 메세지로 받은 6자리 숫자", example = "567843")
    @Size(min = 6, max = 6)
    private String verifyRandomNumber;

    @ApiModelProperty(value = "휴대폰 번호만 입력. 최소 입력 10, 최대 11", example = "01012341234")
    @Size(min = 10, max = 11)
    private String recipientPhoneNumber;
}

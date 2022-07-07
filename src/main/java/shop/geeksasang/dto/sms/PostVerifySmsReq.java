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
    @ApiModelProperty(value = "보안을 위한 랜덤 6자리 숫자")
    @Size(min = 6, max = 6)
    private String verifyRandomNumber;

    @ApiModelProperty(value = "휴대폰 번호만 입력. 최소 입력 10, 최대 11")
    @Size(min = 10, max = 11)
    private String recipientPhoneNumber;
}

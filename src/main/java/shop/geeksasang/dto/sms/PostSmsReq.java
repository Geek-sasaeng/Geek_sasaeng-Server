package shop.geeksasang.dto.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostSmsReq {

    @ApiModelProperty(value = "휴대폰 번호만 입력. 최소 입력 10, 최대 11", example = "01012341234")
    @Pattern(regexp = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$")
    @Size(min = 10, max = 11)
    private String recipientPhoneNumber;


    @NotBlank
    @ApiModelProperty(value = "핸드폰이 가지고 있는 UUID", example = "fe2e3a4d-1748-31f1-be0c-92a35bdfcbd5")
    private String uuid;
}
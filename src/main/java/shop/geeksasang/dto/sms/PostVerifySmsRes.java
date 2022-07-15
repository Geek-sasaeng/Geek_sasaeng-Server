package shop.geeksasang.dto.sms;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostVerifySmsRes {
    @ApiModelProperty(value = "저장된 핸드폰 넘버 인덱스",example = "1")
    private Integer phoneNumberId;
}

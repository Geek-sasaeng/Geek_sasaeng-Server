package shop.geeksasang.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
public class SuccessCommonRes {

    @ApiModelProperty(example = "요청에 성공하셨습니다.", value = "성공 메시지")
    private String message;

    public SuccessCommonRes() {
        this.message = "요청에 성공하셨습니다.";
    }
}

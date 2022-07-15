package shop.geeksasang.dto.email;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class PostEmailCertificationRes {
    @ApiModelProperty(value = "저장된 이메일 인덱스",example = "1")
    private Integer emailId;
}

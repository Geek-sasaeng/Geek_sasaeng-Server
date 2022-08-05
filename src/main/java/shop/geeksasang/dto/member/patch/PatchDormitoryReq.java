package shop.geeksasang.dto.member.patch;



import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Data
public class PatchDormitoryReq {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "기숙사 id (PK 값)", required = true)
    private int dormitoryId; // 기숙사 id
}

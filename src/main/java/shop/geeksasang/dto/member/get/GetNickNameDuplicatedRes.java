package shop.geeksasang.dto.member.get;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;

@Builder // .builder() 사용
@Data // Getter, Setter 포함
public class GetNickNameDuplicatedRes {
    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "중복검사 할 닉네임.")
    private String nickName;
}
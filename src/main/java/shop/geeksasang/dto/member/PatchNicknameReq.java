package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class PatchNicknameReq {

    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "사용자 닉네임", required = true)
    @Size(min = 3, max = 10, message = "최소 3자, 최대10자")
    @NotBlank(message = "변경할 닉네임을 입력해야 합니다.")
    private String nickName; // 새로운 닉네임

    @Builder
    public PatchNicknameReq(String nickName) {
        this.nickName = nickName;
    }

}

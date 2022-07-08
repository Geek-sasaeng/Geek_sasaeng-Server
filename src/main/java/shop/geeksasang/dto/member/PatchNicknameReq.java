package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.Member;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class PatchNicknameReq {

    @ApiModelProperty(value = "닉네임, 최소 5자")
    @Size(min = 5, max = 10, message = "최소 5자, 최대10자")
    @NotBlank(message = "변경할 닉네임을 입력해야 합니다.")
    private String nickName; // 새로운 닉네임

    @Builder
    public PatchNicknameReq(String nickName) {
        this.nickName = nickName;
    }

}

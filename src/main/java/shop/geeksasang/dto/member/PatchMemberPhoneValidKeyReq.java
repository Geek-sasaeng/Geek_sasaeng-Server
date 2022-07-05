package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class PatchMemberPhoneValidKeyReq {

    @ApiModelProperty(value = "휴대폰 인증 번호 입력. 6자리 필수")
    @Size(min=6, max=6)
    private String phoneValidKey;
}

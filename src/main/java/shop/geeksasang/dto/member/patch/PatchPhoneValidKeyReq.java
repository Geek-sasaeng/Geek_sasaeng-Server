package shop.geeksasang.dto.member.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class PatchPhoneValidKeyReq {

    @ApiModelProperty(value = "휴대폰 인증 번호 입력. 6자리 필수",example = "123456")
    @ApiParam(value = "확인 할 폰 인증번호")
    @Size(min=6, max=6)
    private String phoneValidKey;
}

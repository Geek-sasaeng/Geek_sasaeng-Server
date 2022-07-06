package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class PatchInformationAgreeStatusReq {

    @ApiModelProperty(value = "회원정보 동의 여부 입력. 최소 1길이 이상")
    @Size(min=1)
    private String informationAgreeStatus;
}

package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class PatchInformationAgreeStatusReq {

    @ApiModelProperty(value = "회원정보 동의 여부 입력. 최소 1길이 이상",example = "Y")
    @ApiParam(value = "회원정보 동의 여부")
    @NotBlank(message = "정보동의 여부를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String informationAgreeStatus;
}

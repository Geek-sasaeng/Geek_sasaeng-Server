package shop.geeksasang.dto.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.Member;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class PatchPhoneNumberReq {

    @ApiModelProperty(value = "휴대폰 번호만 입력. 최소 입력 10, 최대 11",example = "01012345678")
    @ApiParam(value = "변경할 폰 번호")
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\\\d{3}|\\\\d{4})\\\\d{4}$") // 폰 번호 정규식
    @Size(min=10, max=11)
    private String phoneNumber;

}

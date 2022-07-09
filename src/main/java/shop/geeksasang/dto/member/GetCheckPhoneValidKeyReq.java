package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class GetCheckPhoneValidKeyReq {

    @ApiModelProperty(value = "폰 번호")
    @NotBlank(message = "폰 번호를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String phoneNumber;

    @ApiModelProperty(value = "확인할 폰 인증번호")
    @NotBlank(message = "폰 인증번호를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String phoneValidKey;
}

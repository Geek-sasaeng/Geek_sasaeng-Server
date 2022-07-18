package shop.geeksasang.dto.member.get;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class GetCheckPhoneValidKeyReq {

    @ApiModelProperty(value = "폰 번호", example = "01012341234")
    @ApiParam(value = "폰 번호")
    @NotBlank(message = "폰 번호를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    @Pattern(regexp = "^01(?:0|1|[6-9])(?:\\\\d{3}|\\\\d{4})\\\\d{4}$") // 폰 번호 정규식
    private String phoneNumber;

    @ApiModelProperty(value = "확인할 폰 인증번호",example = "123456")
    @ApiParam(value = "폰 인증번호")
    @NotBlank(message = "폰 인증번호를 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String phoneValidKey;
}

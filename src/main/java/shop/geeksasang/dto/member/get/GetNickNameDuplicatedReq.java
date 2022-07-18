package shop.geeksasang.dto.member.get;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class GetNickNameDuplicatedReq {
    @ApiModelProperty(example = "긱사생")
    @ApiParam(value = "중복검사 할 닉네임.")
    @NotBlank(message = "중복 확인할 닉네임을 입력해야 합니다.") //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String nickName;
}

package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor // 모든 파라미터 생성자
@NoArgsConstructor// 빈 생성자
@Data
public class GetCheckNickNameDuplicatedReq {
    @ApiModelProperty(value = "중복 확인할 닉네임 입력,")
    @NotBlank //Null, 빈 문자열, 스페이스만 있는 문자열 불가
    private String nickName;
}

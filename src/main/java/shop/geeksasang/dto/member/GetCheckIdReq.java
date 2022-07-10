package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class GetCheckIdReq {

    @ApiModelProperty(example = "geeksasaeng")
    @ApiParam(value = "사용자 ID", required = true)
    @Size(min = 6, max = 20)
    @Pattern(regexp="^(?=.*[a-zA-Z])[-a-zA-Z0-9_.]{6,20}$",
            message = "아이디는 6-20자의 영문과 숫자, 일부 특수문자(._-)만 입력 가능합니다.")
    private  String loginId;

}

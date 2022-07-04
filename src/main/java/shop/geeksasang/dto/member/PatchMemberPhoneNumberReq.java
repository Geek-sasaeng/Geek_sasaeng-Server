package shop.geeksasang.dto.member;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.Member;

import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatchMemberPhoneNumberReq {

    @ApiModelProperty(value = "휴대폰 번호만 입력. 최소 입력 10, 최대 11")
    @Size(min=10, max=11)
    private String phoneNumber;

}

package shop.geeksasang.dto.member;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@NoArgsConstructor
@Data
public class PatchMemberStatusReq {

    @ApiModelProperty(value = "비밀번호, 최소 8자")
    @Size(min = 8, max = 15,message = "최소 8자, 최대 15자")
    private  String password;

    @ApiModelProperty(value = "비밀번호 확인용, 최소 8자")
    @Size(min = 8, max = 15,message = "최소 8자, 최대 15자")
    private  String checkPassword;
}

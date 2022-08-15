package shop.geeksasang.dto.deliveryParty.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class PatchLeaveChiefReq {
    @NotBlank
    @ApiModelProperty(example = "2f48f241-9d64-4d16-bf56-70b9d4e0e79a", value = "채팅방 UUID 값" )
    private String uuid;

    @ApiModelProperty(example = "neoneo", value = "유저 닉네임" )
    private String nickName;
}

package shop.geeksasang.dto.deliveryParty.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class PatchLeaveChiefReq {

    @NotNull
    @ApiModelProperty(example = "1", value = "채팅방 ID 값" )
    private Integer partyId;
}

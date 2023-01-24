package shop.geeksasang.dto.deliveryPartyMember.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatchLeaveMemberReq {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달파티의 id", required = true)
    @NotBlank
    private int partyId; //채팅방의 uuid
}

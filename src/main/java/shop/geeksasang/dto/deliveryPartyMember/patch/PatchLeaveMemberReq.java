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

    @ApiModelProperty(example = "ff689bb1-e951-4c9b-b31f-a44552c163a6")
    @ApiParam(value = "배달파티(채팅방)의 UUID", required = true)
    @NotBlank
    private String uuid; //채팅방의 uuid
}

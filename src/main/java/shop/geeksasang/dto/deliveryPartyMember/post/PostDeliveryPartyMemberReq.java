package shop.geeksasang.dto.deliveryPartyMember.post;


import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDeliveryPartyMemberReq {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "파티 id", required = true)
    @NotNull
    private int partyId;



}

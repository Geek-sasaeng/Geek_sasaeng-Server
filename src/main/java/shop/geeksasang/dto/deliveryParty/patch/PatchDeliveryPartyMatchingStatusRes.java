package shop.geeksasang.dto.deliveryParty.patch;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Data
public class PatchDeliveryPartyMatchingStatusRes {

    @ApiModelProperty(example = "154")
    @ApiParam(value = "배달파티 Id")
    private int deliveryPartyId;

    @ApiModelProperty(example = "FINISH")
    @ApiParam(value = "배달파티 매칭상태")
    private String matchingStatus;

}

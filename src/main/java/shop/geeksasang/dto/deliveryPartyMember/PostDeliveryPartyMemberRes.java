package shop.geeksasang.dto.deliveryPartyMember;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryPartyMember;

@Getter
@Setter
@Builder

public class PostDeliveryPartyMemberRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달파티멤버 Id")
    private int deliveryPartyMemberId;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달 파티 Id")
    private int deliveryPartyId;

    static public PostDeliveryPartyMemberRes toDto(DeliveryPartyMember deliveryPartyMember){
        return PostDeliveryPartyMemberRes.builder()
                .deliveryPartyMemberId(deliveryPartyMember.getId())
                .deliveryPartyId(deliveryPartyMember.getParty().getId())
                .build();
    }
}

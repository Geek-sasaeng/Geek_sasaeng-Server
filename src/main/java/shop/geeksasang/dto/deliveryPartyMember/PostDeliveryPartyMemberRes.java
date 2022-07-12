package shop.geeksasang.dto.deliveryPartyMember;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryPartyMember;

@Getter
@Setter
@Builder

public class PostDeliveryPartyMemberRes {
    private int deliveryPartyMemberId;
    private int deliveryPartyId;

    static public PostDeliveryPartyMemberRes toDto(DeliveryPartyMember deliveryPartyMember){
        return PostDeliveryPartyMemberRes.builder()
                .deliveryPartyMemberId(deliveryPartyMember.getId())
                .deliveryPartyId(deliveryPartyMember.getParty().getId())
                .build();
    }
}

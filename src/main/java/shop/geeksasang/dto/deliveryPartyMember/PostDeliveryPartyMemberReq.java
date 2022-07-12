package shop.geeksasang.dto.deliveryPartyMember;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.DeliveryPartyMember;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDeliveryPartyMemberReq {
    private int participantId;
    private int partyId;

    public DeliveryPartyMember toEntity() {
        return DeliveryPartyMember.builder()
                .build();
    }

}

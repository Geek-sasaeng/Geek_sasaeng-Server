package shop.geeksasang.dto.deliveryPartyRoom;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyRoom;
import shop.geeksasang.domain.Member;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PostDeliveryPartyRoomReq {
    private int participantId;
    private int partyId;

    public DeliveryPartyRoom toEntity() {
        return DeliveryPartyRoom.builder()
                .build();
    }

}
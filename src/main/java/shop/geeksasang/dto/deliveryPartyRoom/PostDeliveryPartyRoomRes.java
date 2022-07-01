package shop.geeksasang.dto.deliveryPartyRoom;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryPartyRoom;

@Getter
@Setter
@Builder
public class PostDeliveryPartyRoomRes {
    private int deliveryPartyRoomId;
    private int deliveryPartyId;

    static public PostDeliveryPartyRoomRes toDto(DeliveryPartyRoom deliveryPartyRoom){
        return PostDeliveryPartyRoomRes.builder()
                .deliveryPartyRoomId(deliveryPartyRoom.getId())
                .deliveryPartyId(deliveryPartyRoom.getParty().getId())
                .build();
    }
}

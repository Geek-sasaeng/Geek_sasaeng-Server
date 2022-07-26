package shop.geeksasang.dto.deliveryParty.get;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetDeliveryPartyDefaultLocationRes {

    private Double latitude; //위도
    private Double longitude; //경도

}

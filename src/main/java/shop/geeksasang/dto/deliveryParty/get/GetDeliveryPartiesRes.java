package shop.geeksasang.dto.deliveryParty.get;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class GetDeliveryPartiesRes {

    private boolean isFinalPage;
    private List<DeliveryPartiesVo> deliveryPartiesVoList;

    public GetDeliveryPartiesRes(boolean isFinalPage, List<DeliveryPartiesVo> deliveryPartiesVoList) {
        this.isFinalPage = isFinalPage;
        this.deliveryPartiesVoList = deliveryPartiesVoList;
    }
}

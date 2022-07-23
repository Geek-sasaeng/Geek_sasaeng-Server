package shop.geeksasang.dto.deliveryParty.get;

import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.dto.deliveryParty.get.vo.DeliveryPartiesVo;

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

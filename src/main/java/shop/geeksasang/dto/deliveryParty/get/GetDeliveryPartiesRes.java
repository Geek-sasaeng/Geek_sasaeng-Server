package shop.geeksasang.dto.deliveryParty.get;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.dto.deliveryParty.get.vo.DeliveryPartiesVo;

import java.util.List;

@Getter @Setter
public class GetDeliveryPartiesRes {

    @ApiModelProperty(example = "true")
    @ApiParam(value = "마지막 페이지면 true, 마지막 페이지가 아니면 false")
    private boolean isFinalPage;

    @ApiParam(value = "가져온 배달 파티 리스트")
    private List<DeliveryPartiesVo> deliveryPartiesVoList;

    public GetDeliveryPartiesRes(boolean isFinalPage, List<DeliveryPartiesVo> deliveryPartiesVoList) {
        this.isFinalPage = isFinalPage;
        this.deliveryPartiesVoList = deliveryPartiesVoList;
    }
}

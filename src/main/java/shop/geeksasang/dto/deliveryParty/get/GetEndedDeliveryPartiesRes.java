package shop.geeksasang.dto.deliveryParty.get;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.dto.deliveryParty.get.vo.EndedDeliveryPartiesVo;
import java.util.List;

@Getter
@Setter
public class GetEndedDeliveryPartiesRes {

    @ApiModelProperty(example = "true", value = "마지막 페이지 여부 - 마지막: true / 아니면 : false")
    private boolean isFinalPage;

    @ApiModelProperty(value = "진행했던 배달파티 리스트")
    private List<EndedDeliveryPartiesVo> endedDeliveryPartiesVoList;

    public GetEndedDeliveryPartiesRes(boolean isFinalPage, List<EndedDeliveryPartiesVo> endedDeliveryPartiesVoList){
        this.isFinalPage = isFinalPage;
        this.endedDeliveryPartiesVoList = endedDeliveryPartiesVoList;
    }


}

package shop.geeksasang.dto.deliveryParty.get;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import shop.geeksasang.domain.DeliveryParty;

@Getter
public class GetThreeRecentPartiesRes {

    @ApiModelProperty(example = "1", value = "파티 id")
    private int id;

    @ApiModelProperty(example = "한식 먹자", value = "파티 제목")
    private String title;

    @ApiModelProperty(example = "https://baemin.me/mUpLJ7qBk", value = "배달앱 주소")
    private String storeUrl;

    public GetThreeRecentPartiesRes(int id, String title, String storeUrl) {
        this.id = id;
        this.title = title;
        this.storeUrl = storeUrl;
    }

    public static GetThreeRecentPartiesRes toDto(DeliveryParty deliveryParty) {
        return new GetThreeRecentPartiesRes(deliveryParty.getId(), deliveryParty.getTitle(), deliveryParty.getStoreUrl());
    }
}

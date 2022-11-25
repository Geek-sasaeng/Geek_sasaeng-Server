package shop.geeksasang.dto.deliveryParty.get;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;

@Getter
public class GetRecentOngoingPartiesRes {

    @ApiModelProperty(example = "1", value = "파티 id")
    private int id;

    @ApiModelProperty(example = "한식 먹자", value = "파티 제목")
    private String title;

    @ApiModelProperty(example = "2022-09-11 15:30:00", value = "배달파티 정보 생성 시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String createdAt;


    public GetRecentOngoingPartiesRes(int id, String title, String createdAt) {
        this.id = id;
        this.title = title;
        this.createdAt = createdAt;
    }

    public static GetRecentOngoingPartiesRes toDto(DeliveryParty deliveryParty) {
        return new GetRecentOngoingPartiesRes(deliveryParty.getId(), deliveryParty.getTitle(), deliveryParty.getCreatedAt());
    }
}

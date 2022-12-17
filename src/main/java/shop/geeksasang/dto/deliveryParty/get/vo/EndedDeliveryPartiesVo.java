package shop.geeksasang.dto.deliveryParty.get.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;

@Getter
@Setter
public class EndedDeliveryPartiesVo {
    @ApiModelProperty(example = "1", value = "파티 id")
    private int id;

    @ApiModelProperty(example = "피자 먹을 사람~!",value = "배달 파티 제목")
    private String title;

    @ApiModelProperty(example = "4",value = "배달 파티에 참여할 수 있는 총 인원")
    private int maxMatching;

    @ApiModelProperty(example = "한식", value = "음식 카테고리")
    private String foodCategory;

    @ApiModelProperty(example = "2022-07-11 15:30:00", value = "배달파티 정보 업데이트 시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;

    @Builder
    public EndedDeliveryPartiesVo(int id, String title, int maxMatching, String foodCategory, String updatedAt){
        this.id = id;
        this.title = title;
        this.maxMatching = maxMatching;
        this.foodCategory = foodCategory;
        this.updatedAt = updatedAt;
    }

    public static EndedDeliveryPartiesVo toDto(DeliveryParty deliveryParty) {
        return EndedDeliveryPartiesVo.builder()
                .id(deliveryParty.getId())
                .title(deliveryParty.getTitle())
                .maxMatching(deliveryParty.getMaxMatching())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .updatedAt(deliveryParty.getUpdatedAt())
                .build();
    }
}

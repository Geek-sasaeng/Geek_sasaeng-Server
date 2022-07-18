package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyHashTag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@Builder
public class GetDeliveryPartiesByOrderTimeRes {

    @ApiModelProperty(example = "212")
    @ApiParam(value = "배달파티 ID")
    private int id;

    @ApiModelProperty(example = "규카츠 같이 먹어요.")
    @ApiParam(value = "배달파티 제목")
    private String title;

    @ApiModelProperty(example = "2022-07-13 16:29:30")
    @ApiParam(value = "주문 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "현재까지 매칭 된 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "최대 매칭 인원")
    private int maxMatching;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "태그가 있으면 true, 없으면 false")
    private boolean hasHashTag;

    @ApiModelProperty(example = "한식")
    @ApiParam(value = "음식 카테고리")
    private String foodCategory;

    static public GetDeliveryPartiesByOrderTimeRes toDto(DeliveryParty deliveryParty) {
        return GetDeliveryPartiesByOrderTimeRes.builder()
                .id(deliveryParty.getId())
                .title(deliveryParty.getTitle())
                .orderTime(deliveryParty.getOrderTime())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .hasHashTag(makeHashTagEntityToDto(deliveryParty.getDeliveryPartyHashTags()))
                .build();
    }

    private static boolean makeHashTagEntityToDto(List<DeliveryPartyHashTag> deliveryPartyHashTags) {
        if(deliveryPartyHashTags.isEmpty()){
            return false;
        }
        return true;
    }
}

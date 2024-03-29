package shop.geeksasang.dto.deliveryParty.get.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyHashTag;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Builder
public class DeliveryPartiesVo {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달 파티 id(pk)")
    private int id;

    @ApiModelProperty(example = "피자 먹을 사람~!")
    @ApiParam(value = "배달 파티 제목")
    private String title;

    @ApiModelProperty(example = "2022-07-13 16:29:30")
    @ApiParam(value = "배달 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "현재 배달 파티 참여 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "배달 파티에 참여할 수 있는 총 인원")
    private int maxMatching;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "태그가 있으면 true, 없으면 false")
    private boolean hasHashTag;

    @ApiModelProperty(example = "한식")
    @ApiParam(value = "음식 카테고리")
    private String foodCategory;

    @ApiModelProperty(example = "제 1 기숙사")
    @ApiParam(value = "기숙사 이름")
    private String dormitoryName;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "기숙사 id")
    private Integer dormitoryId;

    //빌더
    static public DeliveryPartiesVo toDto(DeliveryParty deliveryParty){
        return DeliveryPartiesVo.builder()
                .id(deliveryParty.getId())
                .title(deliveryParty.getTitle())
                .orderTime(deliveryParty.getOrderTime())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .hasHashTag(presentHashTag(deliveryParty.getDeliveryPartyHashTags()))
                .dormitoryId(deliveryParty.getDormitory().getId())
                .dormitoryName(deliveryParty.getDormitory().getName())
                .build();
    }

    private static boolean presentHashTag(List<DeliveryPartyHashTag> deliveryPartyHashTags) {
        if(deliveryPartyHashTags.isEmpty()){
            return false;
        }
        return true;
    }
}

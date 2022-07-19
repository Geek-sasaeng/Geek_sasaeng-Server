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

@Getter
@Setter
@Builder
public class GetDeliveryPartiesByKeywordRes {

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

    //빌더
    static public GetDeliveryPartiesByKeywordRes toDto(DeliveryParty deliveryParty){
        return GetDeliveryPartiesByKeywordRes.builder()
                .id(deliveryParty.getId())
                .title(deliveryParty.getTitle())
                .orderTime(deliveryParty.getOrderTime())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .hasHashTag(makeHashTagEntityToDto(deliveryParty.getDeliveryPartyHashTags()))
                .build();
    }

    // 해시태그 id -> title 리스트로 변환
    private static boolean makeHashTagEntityToDto(List<DeliveryPartyHashTag> deliveryPartyHashTags) {
        if(deliveryPartyHashTags.isEmpty()){
            return false;
        }
        return true;
    }

}

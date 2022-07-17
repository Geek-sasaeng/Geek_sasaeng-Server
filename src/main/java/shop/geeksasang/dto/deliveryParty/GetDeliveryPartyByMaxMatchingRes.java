package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.config.domain.OrderTimeCategoryType;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyHashTag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GetDeliveryPartyByMaxMatchingRes {

    @ApiModelProperty(example = "212")
    @ApiParam(value = "배달파티 ID")
    private int id;

    @ApiModelProperty(example = "토마스최")
    @ApiParam(value = "파티장 닉네임")
    private String chief;

    @ApiModelProperty(example = "초밥 같이 먹어요")
    @ApiParam(value = "배달 파티 제목")
    private String title;

    @ApiModelProperty(example = "2022-07-13 16:29:30")
    @ApiParam(value = "주문 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "DINNER")
    @ApiParam(value = "주문시간 카테고리")
    private OrderTimeCategoryType orderTimeCategory;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "현재까지 매칭 된 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "최대 매칭 인원")
    private int maxMatching;

    @ApiModelProperty(example = "제1기숙사 후문")
    @ApiParam(value = "배달 수령 장소")
    private String location;

    @ApiModelProperty(example = "ONGOING")
    @ApiParam(value = "배달 매칭 상태 - 모집중 : ONGOING /  모집마감 : FINISH")
    private MatchingStatus matchingStatus;

    @ApiModelProperty(example = "한식")
    @ApiParam(value = "음식 카테고리")
    private String foodCategory;

    @ApiModelProperty(example = "깉이 먹고 싶어요")
    @ApiParam(value = "해시태그")
    private List<String> hashTags;

    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg")
    @ApiParam(value = "파티장 프로필 이미지 url")
    private String chiefProfileImgUrl;

    static public GetDeliveryPartyByMaxMatchingRes toDto(DeliveryParty deliveryParty) {
        List<String> hashTagDto = makeHashTagEntityToDto(deliveryParty.getDeliveryPartyHashTags());
        return GetDeliveryPartyByMaxMatchingRes.builder()
                .id(deliveryParty.getId())
                .chief(deliveryParty.getChief().getNickName())
                .title(deliveryParty.getTitle())
                .orderTime(deliveryParty.getOrderTime())
                .orderTimeCategory(deliveryParty.getOrderTimeCategory())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .location(deliveryParty.getLocation())
                .matchingStatus(deliveryParty.getMatchingStatus())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .hashTags(hashTagDto)
                .chiefProfileImgUrl(deliveryParty.getChief().getProfileImgUrl())
                .build();
    }
    // 해시태그 id -> title 리스트로 변환
    private static List<String> makeHashTagEntityToDto(List<DeliveryPartyHashTag> deliveryPartyHashTags){
        return deliveryPartyHashTags.stream()
                .map(deliveryPartyHashTag -> deliveryPartyHashTag.getHashTag().getTitle())
                .collect(Collectors.toList());
    }

}

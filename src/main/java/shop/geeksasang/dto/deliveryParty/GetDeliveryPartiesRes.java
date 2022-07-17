package shop.geeksasang.dto.deliveryParty;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyHashTag;
import shop.geeksasang.domain.HashTag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
public class GetDeliveryPartiesRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달 파티 id(pk)")
    private int id;

    @ApiModelProperty(example = "neoneo")
    @ApiParam(value = "배달파티 방장(만든 이)")
    private String chief;

    @ApiModelProperty(example = "야식")
    @ApiParam(value = "음식 카테고리")
    private String foodCategory;

    @ApiModelProperty(example = "피자 먹을 사람~!")
    @ApiParam(value = "배달 파티 제목")
    private String title;

    @ApiModelProperty(example = "배고파요")
    @ApiParam(value = "배달 파티 내용")
    private String content;

    @ApiModelProperty(example = "2022-07-29 20:29:30")
    @ApiParam(value = "배달 시간")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "현재 배달 파티 참여 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "배달 파티에 참여할 수 있는 총 인원")
    private int maxMatching;

    @ApiModelProperty(example = "기숙사 후문")
    @ApiParam(value = "배달시킬 위치")
    private String location;

    @ApiModelProperty(example = "ONGOING")
    @ApiParam(value = "배달 현황")
    private MatchingStatus matchingStatus;

    @ApiModelProperty(example = "깉이 먹고 싶어요")
    @ApiParam(value = "해시태그")
    private List<String> hashTags;

    static public GetDeliveryPartiesRes toDto(DeliveryParty deliveryParty){
        List<String> hashTagDto = makeHashTagEntityToDto(deliveryParty.getDeliveryPartyHashTags());
        return  GetDeliveryPartiesRes.builder()
                .id(deliveryParty.getId())
                .chief(deliveryParty.getChief().getNickName())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .title(deliveryParty.getTitle())
                .content(deliveryParty.getContent())
                .orderTime(deliveryParty.getOrderTime())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .location(deliveryParty.getLocation())
                .matchingStatus(deliveryParty.getMatchingStatus())
                .hashTags(hashTagDto)
                .build();

    }

    private static List<String> makeHashTagEntityToDto(List<DeliveryPartyHashTag> deliveryPartyHashTags) {
        return deliveryPartyHashTags.stream()
                .map(deliveryPartyHashTag -> deliveryPartyHashTag.getHashTag().getTitle())
                .collect(Collectors.toList());
    }
}

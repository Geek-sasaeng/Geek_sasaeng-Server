package shop.geeksasang.dto.deliveryParty;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.HashTag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class GetDeliveryPartyDetailRes {

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달파티 ID")
    private int id;

    @ApiModelProperty(example = "토마스최")
    @ApiParam(value = "파티장 닉네임")
    private String chief;


    @ApiModelProperty(example = "http://geeksasaeng.shop/s3/neo.jpg")
    @ApiParam(value = "파티장 프로필 이미지 url")
    private String chiefProfileImgUrl;

    @ApiModelProperty(example = "한식")
    @ApiParam(value = "음식 카테고리")
    private String category;

    //TODO: 해시태그 엔티티 수정되면 반영
//    @ApiModelProperty(example = "")
//    @ApiParam(value = "사용자 닉네임")
//    private List<String> hashTags;

    @ApiModelProperty(example = "한식 같이 먹어요!!")
    @ApiParam(value = "배달파티 제목")
    private String title;

    @ApiModelProperty(example = "불고기 전골 먹으실분 신청하세요.")
    @ApiParam(value = "배달파티 내용")
    private String content;

    @ApiModelProperty(example = "2022-07-11 15:30:00")
    @ApiParam(value = "주문 예정 시간")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "현재까지 매칭 된 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "최대 미칭 인원")
    private int maxMatching;

    @ApiModelProperty(example = "제1기숙사 후문")
    @ApiParam(value = "배달 수령 장소")
    private String location;

    @ApiModelProperty(example = "ONGOING")
    @ApiParam(value = "배달 매칭 상태, 모집중ONGOING, 모집마감FINISH")
    private MatchingStatus matchingStatus;

    @ApiModelProperty(example = "2022-07-11 15:30:00")
    @ApiParam(value = "배달파티 정보 업데이트 시각")
    private String updatedAt;

    //빌더
    static public GetDeliveryPartyDetailRes toDto(DeliveryParty deliveryParty){
        return GetDeliveryPartyDetailRes.builder()
                .id(deliveryParty.getId())
                .chief(deliveryParty.getChief().getNickName())
                .chiefProfileImgUrl(deliveryParty.getChief().getProfileImgUrl())
                .category(deliveryParty.getFoodCategory().getTitle())
//                .hashTags(deliveryParty.getHashTags().stream().map(HashTag::getTitle).collect(Collectors.toList()))
                .title(deliveryParty.getTitle())
                .content(deliveryParty.getContent())
                .orderTime(deliveryParty.getOrderTime())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .location(deliveryParty.getLocation())
                .matchingStatus(deliveryParty.getMatchingStatus())
                .updatedAt(deliveryParty.getUpdatedAt())
                .build();
    }

}

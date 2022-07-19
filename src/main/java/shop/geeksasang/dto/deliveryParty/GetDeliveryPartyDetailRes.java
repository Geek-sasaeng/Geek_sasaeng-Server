package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyHashTag;


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
    private String foodCategory;

    @ApiModelProperty(example = "깉이 먹고 싶어요")
    @ApiParam(value = "해시태그")
    private List<String> hashTags;

    @ApiModelProperty(example = "한식 같이 먹어요!!")
    @ApiParam(value = "배달파티 제목")
    private String title;

    @ApiModelProperty(example = "불고기 전골 먹으실분 신청하세요.")
    @ApiParam(value = "배달파티 내용")
    private String content;

    @ApiModelProperty(example = "2022-07-11 15:30:00")
    @ApiParam(value = "주문 예정 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2")
    @ApiParam(value = "현재까지 매칭 된 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "최대 미칭 인원")
    private int maxMatching;

//    @ApiModelProperty(example = "제1기숙사 후문")
//    @ApiParam(value = "배달 수령 장소")
//    private String location;

    @ApiModelProperty(example = "ONGOING")
    @ApiParam(value = "배달 매칭 상태, 모집중ONGOING, 모집마감FINISH")
    private MatchingStatus matchingStatus;

    @ApiModelProperty(example = "2022-07-11 15:30:00")
    @ApiParam(value = "배달파티 정보 업데이트 시각")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private String updatedAt;

    @ApiModelProperty(example = "37.456335")
    @ApiParam(value="위도")
    private Double latitude;

    @ApiModelProperty(example = "127.135331")
    @ApiParam(value="경도")
    private Double longitude;

    //빌더
    static public GetDeliveryPartyDetailRes toDto(DeliveryParty deliveryParty){
        List<String> hashTagDto = makeHashTagEntityToDto(deliveryParty.getDeliveryPartyHashTags());
        return GetDeliveryPartyDetailRes.builder()
                .id(deliveryParty.getId())
                .chief(deliveryParty.getChief().getNickName())
                .chiefProfileImgUrl(deliveryParty.getChief().getProfileImgUrl())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .hashTags(hashTagDto)
                .title(deliveryParty.getTitle())
                .content(deliveryParty.getContent())
                .orderTime(deliveryParty.getOrderTime())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .matchingStatus(deliveryParty.getMatchingStatus())
                .updatedAt(deliveryParty.getUpdatedAt())
                .latitude(deliveryParty.getLocation().getLatitude())
                .longitude(deliveryParty.getLocation().getLongitude())
                .build();
    }

    // 해시태그 id -> title 리스트로 변환
    private static List<String> makeHashTagEntityToDto(List<DeliveryPartyHashTag> deliveryPartyHashTags){
        return deliveryPartyHashTags.stream()
                .map(deliveryPartyHashTag -> deliveryPartyHashTag.getHashTag().getTitle())
                .collect(Collectors.toList());
    }

}

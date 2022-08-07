package shop.geeksasang.dto.deliveryParty.put;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryParty;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@Data
public class PutDeliveryPartyRes {
    @ApiModelProperty(example = "geeksasaeng")
    @ApiParam(value = "사용자 닉네임")
    private String chief;

    @ApiModelProperty(example = "1기숙사")
    @ApiParam(value = "기숙사 이름")
    private String dormitory;

    @ApiModelProperty(example = "한식")
    @ApiParam(value = "음식 카테고리")
    private String foodCategory;

    @ApiModelProperty(example = "초밥 같이 먹어요")
    @ApiParam(value = "배달 파티 제목")
    private String title;

    @ApiModelProperty(example = "편하게 채팅 주세요")
    @ApiParam(value = "배달 파티 내용")
    private String content;

    @ApiModelProperty(example = "2022-07-13 16:29:30")
    @ApiParam(value = "주문 시간")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    @ApiModelProperty(example = "2022-07-13 13:29:30")
    @ApiParam(value = "배달 파티 수정 시간")
    private String updatedAt;

    @ApiModelProperty(example = "DINNER")
    @ApiParam(value = "주문시간 카테고리")
    private String orderTimeCategoryType;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달파티 현재 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "배달 파티 총 인원")
    private int maxMatching;

    @ApiModelProperty(example = "ONGOING")
    @ApiParam(value = "주문 진행 상태")
    private String matchingStatus;

    @ApiModelProperty(example = "https://baemin.me/mUpLJ7qBk")
    @ApiParam(value = "배달 업소 url")
    private String storeUrl;

    @ApiModelProperty(example = "37.456335")
    @ApiParam(value="위도")
    private Double latitude;

    @ApiModelProperty(example = "127.135331")
    @ApiParam(value="경도")
    private Double longitude;

    @ApiModelProperty(example = "true")
    @ApiParam(value = "해시태그 추가 여부")
    private boolean hashTag;


    static public PutDeliveryPartyRes toDto(DeliveryParty deliveryParty){

        return PutDeliveryPartyRes.builder()
                .chief(deliveryParty.getChief().getNickName())
                .dormitory(deliveryParty.getDormitory().getName())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .title(deliveryParty.getTitle())
                .content(deliveryParty.getContent())
                .orderTime(deliveryParty.getOrderTime())
                .updatedAt(deliveryParty.getUpdatedAt())
                .orderTimeCategoryType(deliveryParty.getOrderTimeCategory().toString())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .matchingStatus(deliveryParty.getMatchingStatus().toString())
                .storeUrl(deliveryParty.getStoreUrl())
                .latitude(deliveryParty.getLocation().getLatitude())
                .longitude(deliveryParty.getLocation().getLongitude())
                .hashTag(!deliveryParty.getDeliveryPartyHashTags().isEmpty())
                .build();
    }














}

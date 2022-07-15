package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@Data
public class PostDeliveryPartyRes {

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

    @ApiModelProperty(example ="[" +"매칭 시 바로 주문" +","+"같이 먹고 싶어요" + "]")
    @ApiParam(value = "해시태그 내용")
    private List<String> hashTags;

    @ApiModelProperty(example = "2022-07-13 13:29:30")
    @ApiParam(value = "배달 파티 생성 시간")
    private String createdAt;

    @ApiModelProperty(example = "DINNER")
    @ApiParam(value = "주문시간 카테고리")
    private String orderTimeCategoryType;

    @ApiModelProperty(example = "1")
    @ApiParam(value = "배달파티 현재 인원")
    private int currentMatching;

    @ApiModelProperty(example = "4")
    @ApiParam(value = "배달 파티 총 인원")
    private int maxMatching;

    @ApiModelProperty(example = "1기숙사")
    @ApiParam(value = "수령 장소")
    private String location;

    @ApiModelProperty(example = "ONGOING")
    @ApiParam(value = "주문 진행 상태")
    private String matchingStatus;

    static public PostDeliveryPartyRes toDto(DeliveryParty deliveryParty){

        return PostDeliveryPartyRes.builder()
                .chief(deliveryParty.getChief().getNickName())
                .dormitory(deliveryParty.getDormitory().getName())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .title(deliveryParty.getTitle())
                .content(deliveryParty.getContent())
                .orderTime(deliveryParty.getOrderTime())
                .hashTags(deliveryParty.getHashTags().stream().map(HashTag::getTitle).collect(Collectors.toList()))
                .createdAt(deliveryParty.getCreatedAt())
                .orderTimeCategoryType(deliveryParty.getOrderTimeCategory().toString())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .location(deliveryParty.getLocation())
                .matchingStatus(deliveryParty.getMatchingStatus().toString())
                .build();
    }
}

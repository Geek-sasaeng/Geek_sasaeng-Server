package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
public class PostDeliveryPartyRes {
    private String chief;
    private String dormitory;
    private String foodCategory;
    private String title;
    private String content;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss ", timezone = "Asia/Seoul")
    private LocalDateTime orderTime;

    private List<String> hashTags;

    private String createdAt;
    private String orderTimeCategoryType;
    private int currentMatching;
    private int maxMatching;
    private String location;
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

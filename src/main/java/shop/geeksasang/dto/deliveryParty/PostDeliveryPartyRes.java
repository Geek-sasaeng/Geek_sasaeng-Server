package shop.geeksasang.dto.deliveryParty;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.*;

import java.time.LocalDateTime;


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

    private String createdAt;

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
                .createdAt(deliveryParty.getCreatedAt())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .location(deliveryParty.getLocation())
                .matchingStatus(deliveryParty.getMatchingStatus().toString())
                .build();
    }
}

package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryParty;
import java.time.LocalDateTime;



@Getter
@Setter
@Builder
public class GetDeliveryPartyRes {
    private String chief;
    private String dormitory;
    private String foodCategory;
    private String title;
    private String content;
    private LocalDateTime orderTime;
    private int currentMatching;
    private int maxMatching;
    private String location;
    private String matchingStatus;

    static public GetDeliveryPartyRes toDto(DeliveryParty deliveryParty){
        return GetDeliveryPartyRes.builder()
                .chief(deliveryParty.getChief().getNickName())
                .dormitory(deliveryParty.getDormitory().getName())
                .foodCategory(deliveryParty.getFoodCategory().getTitle())
                .title(deliveryParty.getTitle())
                .content(deliveryParty.getContent())
                .orderTime(deliveryParty.getOrderTime())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .location(deliveryParty.getLocation())
                .matchingStatus(deliveryParty.getMatchingStatus().toString())// TODO:#####
                .build();
    }
}

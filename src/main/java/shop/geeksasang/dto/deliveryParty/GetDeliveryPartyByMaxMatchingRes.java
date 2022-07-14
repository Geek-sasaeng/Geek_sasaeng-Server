package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.config.domain.OrderTimeCategoryType;
import shop.geeksasang.domain.DeliveryParty;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetDeliveryPartyByMaxMatchingRes {

    private int id;
    private String chief;
    private String title;
    private LocalDateTime orderTime;
    private OrderTimeCategoryType orderTimeCategory;
    private int currentMatching;
    private int maxMatching;
    private String location;
    private MatchingStatus matchingStatus;
    private String foodCategory;

    static public GetDeliveryPartyByMaxMatchingRes toDto(DeliveryParty deliveryParty) {
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
                .build();
    }
}

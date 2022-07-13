package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.config.domain.OrderTimeCategoryType;
import shop.geeksasang.domain.DeliveryParty;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
public class GetDeliveryPartyByOrderTimeRes {

    private int id;
    private String title;
    private LocalDateTime orderTime;
    private OrderTimeCategoryType orderTimeCategory;
    private int currentMatching;
    private int maxMatching;
    private MatchingStatus matchingStatus;
    private String food_category;

    static public GetDeliveryPartyByOrderTimeRes toDto(DeliveryParty deliveryParty) {
        return GetDeliveryPartyByOrderTimeRes.builder()
                .id(deliveryParty.getId())
                .title(deliveryParty.getTitle())
                .orderTime(deliveryParty.getOrderTime())
                .orderTimeCategory(deliveryParty.getOrderTimeCategory())
                .currentMatching(deliveryParty.getCurrentMatching())
                .maxMatching(deliveryParty.getMaxMatching())
                .matchingStatus(deliveryParty.getMatchingStatus())
                .food_category(deliveryParty.getFood_category().getTitle())
                .build();
    }
}

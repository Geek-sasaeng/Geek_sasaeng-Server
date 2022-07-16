package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.domain.DeliveryParty;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class GetDeliveryPartiesByKeywordRes {

    private int id;
    private String chief;
    private String foodCategory;
    private String title;
    private String content;
    private LocalDateTime orderTime;
    private int currentMatching;
    private int maxMatching;
    private String location;
    private MatchingStatus matchingStatus;

    //빌더
    static public GetDeliveryPartiesByKeywordRes toDto(DeliveryParty deliveryParty){
        return GetDeliveryPartiesByKeywordRes.builder()
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
                .build();
    }

}

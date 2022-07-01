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
    private String domitory;
    //    private List<HashTag> hashTags;
    //private String hashTag; //TODO:######
    private String category;
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
                .domitory(deliveryParty.getDomitory().getName())
                //.hashTags(deliveryParty.getHashTag()) // Req의 메소드와 다름
                //.hashTag(deliveryParty.getHashTag()) // TODO:#####
                .category(deliveryParty.getCategory().getTitle())
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

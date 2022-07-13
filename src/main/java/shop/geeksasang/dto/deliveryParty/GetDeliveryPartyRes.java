package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.HashTag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
public class GetDeliveryPartyRes {
    private String chief;
    private String domitory;
    //    private List<HashTag> hashTags;
    //private String hashTag; //TODO:######
    private List<String> hashTags;
    private String food_category;
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
                .hashTags(deliveryParty.getHashTags().stream().map(HashTag::getTitle).collect(Collectors.toList()))
                .food_category(deliveryParty.getFood_category().getTitle())
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

package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.domain.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
public class PostDeliveryPartyRes {
    private String chief;
    private String domitory;
//    private List<HashTag> hashTags;
    //private String hashTag; //TODO:######
    private List<String> hashTags;
    private String category;
    private String title;
    private String content;
    private LocalDateTime orderTime;
    private int currentMatching;
    private int maxMatching;
    private String location;
    private String matchingStatus;



    static public PostDeliveryPartyRes toDto(DeliveryParty deliveryParty){


        return PostDeliveryPartyRes.builder()
                .chief(deliveryParty.getChief().getNickName())
                .domitory(deliveryParty.getDomitory().getName())
                //.hashTags(deliveryParty.getHashTag()) // Req의 메소드와 다름
                //.hashTags(deliveryParty.getHashTag()) // TODO:#####
                .hashTags(deliveryParty.getHashTag().stream().map(HashTag::getTitle).collect(Collectors.toList()))
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

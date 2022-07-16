package shop.geeksasang.dto.deliveryParty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.DeliveryPartyHashTag;
import shop.geeksasang.domain.HashTag;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@Builder
public class GetDeliveryPartiesRes {
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
    private List<String> hashTags;

    static public GetDeliveryPartiesRes toDto(DeliveryParty deliveryParty){
        List<String> hashTagDto = makeHashTagEntityToDto(deliveryParty.getDeliveryPartyHashTags());
        return  GetDeliveryPartiesRes.builder()
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
                .hashTags(hashTagDto)
                .build();

    }

    private static List<String> makeHashTagEntityToDto(List<DeliveryPartyHashTag> deliveryPartyHashTags) {
        return deliveryPartyHashTags.stream()
                .map(deliveryPartyHashTag -> deliveryPartyHashTag.getHashTag().getTitle())
                .collect(Collectors.toList());
    }
}

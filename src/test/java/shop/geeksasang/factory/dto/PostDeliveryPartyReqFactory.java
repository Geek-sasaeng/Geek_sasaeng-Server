package shop.geeksasang.factory.dto;

import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;

import java.time.LocalDateTime;

public class PostDeliveryPartyReqFactory {

    public static PostDeliveryPartyReq create(){
        return PostDeliveryPartyReq.builder()
                .foodCategory(1)
                .title("party")
                .content("content")
                .orderTime(LocalDateTime.now())
                .maxMatching(8)
                .storeUrl("url")
                .latitude(1.11)
                .longitude(1.22)
                .hashTag(true)
                .build();
    }
}

package shop.geeksasang.factory.dto.deliveryparty;

import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PostDeliveryPartyFactory {

    public static PostDeliveryPartyReq createReq(int foodCategoryId){
        return PostDeliveryPartyReq.builder()
                .title("party")
                .content("content")
                .orderTime(LocalDateTime.parse("2024-12-30 20:29:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) //이렇게 하지 않으면 오류가 계속 발생.
                .maxMatching(8)
                .storeUrl("url")
                .latitude(1.11)
                .longitude(1.22)
                .hashTag(false)
                .bank("TOSS")
                .foodCategory(foodCategoryId)
                .accountNumber("111-111-111111")
                .chatRoomName("chatRoomName")
                .build();
    }

    public static PostDeliveryPartyReq createReqV2(int foodCategoryId){
        return PostDeliveryPartyReq.builder()
                .title("party")
                .content("content")
                .orderTime(LocalDateTime.parse("2024-12-30 20:29:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))) //이렇게 하지 않으면 오류가 계속 발생.
                .maxMatching(3)
                .storeUrl("url")
                .latitude(1.11)
                .longitude(1.22)
                .hashTag(false)
                .bank("TOSS")
                .foodCategory(foodCategoryId)
                .accountNumber("111-111-111111")
                .chatRoomName("chatRoomName")
                .build();
    }

    public static PostDeliveryPartyRes createRes(){
        return PostDeliveryPartyRes.builder()
                .title("party")
                .content("content")
                .orderTime(LocalDateTime.parse("2022-08-30 20:29:30", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .maxMatching(8)
                .storeUrl("url")
                .latitude(1.11)
                .longitude(1.22)
                .hashTag(false)
                .build();
    }
}


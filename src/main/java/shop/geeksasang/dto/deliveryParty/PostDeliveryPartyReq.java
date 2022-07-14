package shop.geeksasang.dto.deliveryParty;

import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import shop.geeksasang.domain.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDeliveryPartyReq {
    private int chief; // 추후 jwt 이용

    private int dormitory;

    private int foodCategory;

    private List<Integer> hashTag;

    private String title;

    private String content;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private LocalDateTime orderTime; //주문시간

    private int maxMatching;

    private String location;

    // 외래키 참조하는 것 말고는  요청 엔티티 생성
    public DeliveryParty toEntity() {
        return DeliveryParty.builder()
                .title(getTitle())
                .content(getContent())
                .orderTime(getOrderTime())
                .maxMatching(getMaxMatching())
                .location(getLocation())
                .build();
    }
}

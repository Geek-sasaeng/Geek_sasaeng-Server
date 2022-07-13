package shop.geeksasang.dto.deliveryParty;

import lombok.*;
import net.bytebuddy.asm.Advice;
import org.springframework.format.annotation.DateTimeFormat;
import shop.geeksasang.config.domain.MatchingStatus;
import shop.geeksasang.domain.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDeliveryPartyReq {
    private int chief; // 추후 jwt 이용

    private int dormitory;

    private int foodCategory;

    //**property 추가되어야 함.**

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

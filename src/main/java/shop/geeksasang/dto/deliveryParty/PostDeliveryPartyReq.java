package shop.geeksasang.dto.deliveryParty;

import lombok.*;
import net.bytebuddy.asm.Advice;
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
    private int chief;
    private int domitory;
    private List<Integer> hashTag;
    private int foodCategory;
    private String title;
    private String content;
    private LocalDateTime orderTime; // TODO: ###########
    private int currentMatching;
    private int maxMatching;
    private String location;
    private MatchingStatus matchingStatus; // TODO: #########String으로 하면 에러 남

    // 외래키 참조하는 것 말고는  요청 엔티티 생성
    public DeliveryParty toEntity() {
        return DeliveryParty.builder()
                .title(getTitle())
                .content(getContent())
                .orderTime(getOrderTime())
                .currentMatching(getCurrentMatching())
                .maxMatching(getMaxMatching())
                .location(getLocation())
                .matchingStatus(getMatchingStatus())
                .build();
    }
}

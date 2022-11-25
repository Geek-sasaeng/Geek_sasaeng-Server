package shop.geeksasang.domain.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document //@Document는객체를 몽고DB에 영속화시킴 = SpringDataJpa의 @Entity와 같은 역할
@ToString
@Getter
@NoArgsConstructor
public class ParticipantInfo {
    @Id
    private Long id;

    private LocalDateTime enterTime;
    private boolean isRemittance;
    private Long memberId;

    public ParticipantInfo(LocalDateTime enterTime, boolean isRemittance, Long memberId) {
        this.enterTime = enterTime;
        this.isRemittance = isRemittance;
        this.memberId = memberId;
    }
}

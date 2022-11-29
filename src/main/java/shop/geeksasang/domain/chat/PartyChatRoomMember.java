package shop.geeksasang.domain.chat;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.time.LocalDateTime;


@Document //@Document는객체를 몽고DB에 영속화시킴 = SpringDataJpa의 @Entity와 같은 역할
@ToString
@Getter
@NoArgsConstructor
public class PartyChatRoomMember {

    @Id
    private String id;

    private int memberId;
    private LocalDateTime enterTime;
    private boolean isRemittance;

    @DocumentReference(lazy = true) // 다대일
    private PartyChatRoom partyChatRoom;

    private String email;

    public PartyChatRoomMember(LocalDateTime enterTime, boolean isRemittance, int memberId) {
        this.enterTime = enterTime;
        this.isRemittance = isRemittance;
        this.memberId = memberId;
    }
}

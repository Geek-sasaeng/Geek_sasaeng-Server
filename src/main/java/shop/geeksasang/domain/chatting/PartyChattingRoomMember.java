package shop.geeksasang.domain.chatting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document //@Document는객체를 몽고DB에 영속화시킴 = SpringDataJpa의 @Entity와 같은 역할
@ToString
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PartyChattingRoomMember {

    @Id
    private String id;

    private int memberId;

//    @DocumentReference(lazy = true) // 다대일
//    private PartyChattingRoom partyChattingRoom;

}

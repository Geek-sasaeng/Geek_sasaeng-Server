package shop.geeksasang.domain.chat;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Unwrapped;
import shop.geeksasang.config.domain.BaseEntityMongo;

import java.time.LocalDateTime;


@Document //@Document는객체를 몽고DB에 영속화시킴 = SpringDataJpa의 @Entity와 같은 역할
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

    //일단 여기 추가.
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    protected BaseEntityMongo baseEntityMongo;

    private boolean checkedOut;


    public PartyChatRoomMember(LocalDateTime enterTime, boolean isRemittance, int memberId) {
        this.enterTime = enterTime;
        this.isRemittance = isRemittance;
        this.memberId = memberId;
        this.baseEntityMongo = new BaseEntityMongo();
        this.checkedOut = false;
    }

    //이건 빼버리고 싶은데, 추후 토론.
    public PartyChatRoomMember(int memberId, LocalDateTime enterTime, boolean isRemittance, PartyChatRoom partyChatRoom, String email) {
        this.memberId = memberId;
        this.enterTime = enterTime;
        this.isRemittance = isRemittance;
        this.partyChatRoom = partyChatRoom;
        this.email = email;
        this.baseEntityMongo = new BaseEntityMongo();
        this.checkedOut = false;
    }

    public boolean alreadyRemit() {
        return isRemittance;
    }

    public void delete() {
        this.partyChatRoom = null;
        this.baseEntityMongo.delete();
        this.checkedOut = true;
    }

    public void enterRoom(PartyChatRoom chatRoom) {
        this.partyChatRoom = chatRoom;
    }

    public LocalDateTime getLastChatAt() {
        return this.partyChatRoom.getLastChatAt();
    }

    public void forceOut() {
        this.baseEntityMongo.forceOut();
    }


    @Override
    public String toString() {
        return "PartyChatRoomMember{" +
                "id='" + id + '\'' +
                ", memberId=" + memberId +
                ", enterTime=" + enterTime +
                ", isRemittance=" + isRemittance +
                ", email='" + email + '\'' +
                ", baseEntityMongo=" + baseEntityMongo +
                ", checkedOut=" + checkedOut +
                '}';
    }
}

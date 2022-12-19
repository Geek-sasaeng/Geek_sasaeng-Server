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

    //일단 여기 추가.
    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    protected BaseEntityMongo baseEntityMongo;

    public PartyChatRoomMember(LocalDateTime enterTime, boolean isRemittance, int memberId) {
        this.enterTime = enterTime;
        this.isRemittance = isRemittance;
        this.memberId = memberId;
        this.baseEntityMongo = new BaseEntityMongo();
    }

    //이건 빼버리고 싶은데, 추후 토론.
    public PartyChatRoomMember(int memberId, LocalDateTime enterTime, boolean isRemittance, PartyChatRoom partyChatRoom, String email) {
        this.memberId = memberId;
        this.enterTime = enterTime;
        this.isRemittance = isRemittance;
        this.partyChatRoom = partyChatRoom;
        this.email = email;
        this.baseEntityMongo = new BaseEntityMongo();
    }

    public boolean alreadyRemit() {
        return isRemittance;
    }

    public void delete() {
        this.partyChatRoom = null;
        this.baseEntityMongo.delete();
    }

    public void enterRoom(PartyChatRoom chatRoom) {
        this.partyChatRoom = chatRoom;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartyChatRoomMember)) return false;

        PartyChatRoomMember that = (PartyChatRoomMember) o;

        if (getMemberId() != that.getMemberId()) return false;
        if (isRemittance() != that.isRemittance()) return false;
        if (getId() != null ? !getId().equals(that.getId()) : that.getId() != null) return false;
        if (getEnterTime() != null ? !getEnterTime().equals(that.getEnterTime()) : that.getEnterTime() != null)
            return false;
        if (getPartyChatRoom() != null ? !getPartyChatRoom().equals(that.getPartyChatRoom()) : that.getPartyChatRoom() != null)
            return false;
        if (getEmail() != null ? !getEmail().equals(that.getEmail()) : that.getEmail() != null) return false;
        return getBaseEntityMongo() != null ? getBaseEntityMongo().equals(that.getBaseEntityMongo()) : that.getBaseEntityMongo() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + getMemberId();
        result = 31 * result + (getEnterTime() != null ? getEnterTime().hashCode() : 0);
        result = 31 * result + (isRemittance() ? 1 : 0);
        result = 31 * result + (getPartyChatRoom() != null ? getPartyChatRoom().hashCode() : 0);
        result = 31 * result + (getEmail() != null ? getEmail().hashCode() : 0);
        result = 31 * result + (getBaseEntityMongo() != null ? getBaseEntityMongo().hashCode() : 0);
        return result;
    }
}

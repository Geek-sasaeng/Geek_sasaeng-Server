package shop.geeksasang.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import shop.geeksasang.config.status.OrderStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Document
@Getter
@NoArgsConstructor
public class PartyChatRoom extends ChatRoom{

    private String title;

    @DocumentReference // 일대다
    private List<Chat> chats = new ArrayList<>();

    @DocumentReference()
    private PartyChatRoomMember chief;

    @DocumentReference()
    private List<PartyChatRoomMember> participants = new ArrayList<>();

    private String accountNumber;
    private String bank;
    private String category;
    private Boolean isFinish; //채팅방 입장 마감 여부
    private Integer maxMatching;
    private int deliveryPartyId;
    private OrderStatus orderStatus;
    private LocalDateTime lastChatAt; // 가장 최근 메시지 시각

    public PartyChatRoom(String title, List<Chat> chats, List<PartyChatRoomMember> participants, String accountNumber,
                         String bank, String category, Boolean isFinish, Integer maxMatching, PartyChatRoomMember chief, int deliveryPartyId, LocalDateTime lastChatAt) {
        super();
        this.title = title;
        this.chats = chats;
        this.participants = participants;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.category = category;
        this.isFinish = isFinish;
        this.maxMatching = maxMatching;
        this.chief = chief;
        this.deliveryPartyId = deliveryPartyId;
        this.orderStatus = OrderStatus.BEFORE_ORDER;
        this.lastChatAt = lastChatAt;
    }

    @Override
    public String toString() {
        return "DeliveryPartyChatRoom{" +
                "id='" + id + '\'' +
                ", baseEntityMongo=" + baseEntityMongo +
                ", title='" + title + '\'' +
                '}';
    }

    public void addParticipants(PartyChatRoomMember partyChatRoomMember){
        this.participants.add(partyChatRoomMember);
    }

    public boolean isNotChief(PartyChatRoomMember chief) {
        return !chief.getId().equals(this.chief.getId());
    }

    public void removeParticipant(PartyChatRoomMember removedMember) {
        this.participants.remove(removedMember);
    }

    public boolean isOnlyChief() {
        return participants.size() == 1;
    }

    public boolean existAlreadyRemittanceParticipant() {
        List<PartyChatRoomMember> list = participants
                .stream()
                .filter(m -> m.isRemittance())
                .collect(Collectors.toList());
        return list.size() > 0;
    }

    public void deleteChief() {
        this.chief = null;
    }

    public PartyChatRoomMember changeChief() {
        participants.remove(0);
        PartyChatRoomMember changeChief = this.participants.get(0);
        this.chief = changeChief;
        return changeChief;
    }

    public void changeLastChatAt(LocalDateTime lastChatAt){
        this.lastChatAt = lastChatAt;}

    public void changeIsFinishToTrue(){
        this.isFinish = true;
    }
}
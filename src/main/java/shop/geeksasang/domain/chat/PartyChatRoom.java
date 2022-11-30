package shop.geeksasang.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

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
    private Boolean isFinish;
    private Integer maxMatching;

    public PartyChatRoom(String title, List<Chat> chats, List<PartyChatRoomMember> participants, String accountNumber,
                         String bank, String category, Boolean isFinish, Integer maxMatching, PartyChatRoomMember chief) {
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

}
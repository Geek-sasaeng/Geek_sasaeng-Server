package shop.geeksasang.domain.chatting;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;

import java.util.ArrayList;
import java.util.List;

@Document
@Getter
@NoArgsConstructor
public class PartyChattingRoom extends ChattingRoom{

    private String title;

    //TODO:채팅과 일대다 연관관계 테스트 중
    @DocumentReference // 일대다
    private List<Chatting> chattings = new ArrayList<>();

    private List<ParticipantInfo> participants = new ArrayList<>();

//    @DocumentReference // 일대다
//    private List<PartyChattingRoomMember> participants = new ArrayList<>();

    private String accountNumber;
    private String bank;
    private String category;
    private Boolean isFinish;
    private Integer maxMatching;

    public PartyChattingRoom(String title) {
        super();
        this.title = title;
    }

    public PartyChattingRoom(String title, List<Chatting> chattings) {
        super();
        this.title = title;
        this.chattings = chattings;
    }

    public PartyChattingRoom(String title, List<Chatting> chattings, List<ParticipantInfo> participants, String accountNumber, String bank, String category, Boolean isFinish, Integer maxMatching) {
        super();
        this.title = title;
        this.chattings = chattings;
        this.participants = participants;
        this.accountNumber = accountNumber;
        this.bank = bank;
        this.category = category;
        this.isFinish = isFinish;
        this.maxMatching = maxMatching;
    }

    @Override
    public String toString() {
        return "DeliveryPartyChattingRoom{" +
                "id='" + id + '\'' +
                ", baseEntityMongo=" + baseEntityMongo +
                ", title='" + title + '\'' +
                '}';
    }

    public void changeParticipants(ParticipantInfo participantInfo){
        this.participants.add(participantInfo);
    }

}
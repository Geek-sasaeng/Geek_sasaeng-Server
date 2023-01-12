package shop.geeksasang.domain.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DocumentReference;
import org.springframework.data.mongodb.core.mapping.Unwrapped;
import shop.geeksasang.config.domain.BaseEntityMongo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//Serializable : 자바에서 직렬화를 통해 object를 빠르게 보낼 수 있음/ 외부로 데이터 보내는데 필요?
@Document //@Document는객체를 몽고DB에 영속화시킴 = SpringDataJpa의 @Entity와 같은 역할
@ToString
@Getter
@NoArgsConstructor
public class Chat implements Serializable {

    @Id
    public String id;

    public String content;

    @DocumentReference(lazy = true) // 다대일
    private PartyChatRoom partyChatRoom;

    private Boolean isSystemMessage;

    @DocumentReference(lazy = true)
    private PartyChatRoomMember partyChatRoomMember;

    private String profileImgUrl;

    private List<Integer> readMembers = new ArrayList<>(); // 읽은 멤버 ID 리스트

    private Boolean isImageMessage;

    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    private BaseEntityMongo baseEntityMongo;

    public Chat(String content) {
        this.content = content;
        this.baseEntityMongo = new BaseEntityMongo();
    }

    public Chat(String content, PartyChatRoom partyChatRoom, Boolean isSystemMessage, PartyChatRoomMember partyChatRoomMember, String profileImgUrl, List<Integer> readMembers) {
        this.content = content;
        this.partyChatRoom = partyChatRoom;
        this.isSystemMessage = isSystemMessage;
        this.partyChatRoomMember = partyChatRoomMember;
        this.profileImgUrl = profileImgUrl;
        this.readMembers = readMembers;
        this.isImageMessage = false;
        this.baseEntityMongo = new BaseEntityMongo();
    }

    public Chat(String content, PartyChatRoom partyChatRoom, Boolean isSystemMessage, PartyChatRoomMember partyChatRoomMember, String profileImgUrl, List<Integer> readMembers, Boolean isimageMessage) {
        this.content = content;
        this.partyChatRoom = partyChatRoom;
        this.isSystemMessage = isSystemMessage;
        this.partyChatRoomMember = partyChatRoomMember;
        this.profileImgUrl = profileImgUrl;
        this.readMembers = readMembers;
        this.isImageMessage = isimageMessage;
        this.baseEntityMongo = new BaseEntityMongo();
    }

    /*
    연관관계 편의 메서드
     */
    public void addReadMember(Integer memberId){
        this.readMembers.add(memberId);
    }

    public int getUnreadMemberCnt(){
        return this.partyChatRoom.getParticipants().size() - this.readMembers.size();
    }
}

/**
 * @Unwrapped
 * 값 개체를 대상 문서에서 평평하게 구성하는 주석
 * 결과 집합에서 읽을 때 랩되지 않은 모든 값이 null인 경우 onEmpty() 값에 따라 속성이 null 또는 빈 인스턴스로 설정
 * 쉽게말해 다른 클래스를 unwrapping 하면 그 안에있는 필드값들을 클래스에 바로 포함시킬수 있다.**/

package shop.geeksasang.domain.chat;

import lombok.Getter;
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
public class Chat implements Serializable {

    @Id
    public String id;

    public String content;

    @DocumentReference(lazy = true) // 다대일
    private PartyChatRoom partyChatRoom;

    private Boolean isSystemMessage;

    private String nickName;

    private String profileImgUrl;

    private List<Long> readMembers = new ArrayList<>(); // 읽은 멤버 ID 리스트

    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    private BaseEntityMongo baseEntityMongo;

    public Chat(String content) {
        this.content = content;
        this.baseEntityMongo = new BaseEntityMongo();
    }

    public Chat(String content, PartyChatRoom partyChatRoom, Boolean isSystemMessage, String nickName, String profileImgUrl, List<Long> readMembers, BaseEntityMongo baseEntityMongo) {
        this.content = content;
        this.partyChatRoom = partyChatRoom;
        this.isSystemMessage = isSystemMessage;
        this.nickName = nickName;
        this.profileImgUrl = profileImgUrl;
        this.readMembers = readMembers;
        this.baseEntityMongo = baseEntityMongo;
    }
}

/**
 * @Unwrapped
 * 값 개체를 대상 문서에서 평평하게 구성하는 주석
 * 결과 집합에서 읽을 때 랩되지 않은 모든 값이 null인 경우 onEmpty() 값에 따라 속성이 null 또는 빈 인스턴스로 설정
 * 쉽게말해 다른 클래스를 unwrapping 하면 그 안에있는 필드값들을 클래스에 바로 포함시킬수 있다.**/

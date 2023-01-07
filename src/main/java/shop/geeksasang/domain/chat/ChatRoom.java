package shop.geeksasang.domain.chat;

import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Unwrapped;
import shop.geeksasang.config.domain.BaseEntityMongo;


//@Document  //추상 클래스로 때려 박을 수 있음. 신기
@Getter
public abstract class ChatRoom { // 배달, 중고거래, 커뮤니티 등 서로다른 분류의 채팅방 공통 부분 추상 클래스

    @Id
    protected String id;

    @Unwrapped(onEmpty = Unwrapped.OnEmpty.USE_EMPTY)
    protected BaseEntityMongo baseEntityMongo;

    public ChatRoom() {
        this.baseEntityMongo = new BaseEntityMongo();
    }

    public String getId() {
        return id;
    }


}

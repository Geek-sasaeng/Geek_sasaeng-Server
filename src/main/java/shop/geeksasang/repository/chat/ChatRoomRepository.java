package shop.geeksasang.repository.chat;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.ChatRoom;

import java.util.List;

@Repository
public interface ChatRoomRepository extends MongoRepository<ChatRoom, String> {

    @Query("{id:'?0'}") // 0번째 파라미터 조건
    List<ChatRoom> findAllByPartyChatRoomId(String id);


}

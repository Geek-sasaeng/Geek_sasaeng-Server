package shop.geeksasang.mongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.PartyChatRoom;

import java.util.Optional;

@Repository
public interface PartyChatRoomRepository extends MongoRepository<PartyChatRoom, String> {
    @Query("{id:'?0'}") // 0번째 파라미터 조건
    Optional<PartyChatRoom> findByPartyChatRoomId(String id);
}

package shop.geeksasang.mongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chatting.Chatting;

@Repository
public interface ChattingRepository extends MongoRepository<Chatting, String> {
}

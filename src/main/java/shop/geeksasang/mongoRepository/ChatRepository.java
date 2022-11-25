package shop.geeksasang.mongoRepository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.Chat;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {
}

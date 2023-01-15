package shop.geeksasang.repository.chat;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.Chat;

import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    @Query("{'_id' : :#{#id}, 'status' : 'ACTIVE'}")
    Optional<Chat> findByChatId(@Param("id") ObjectId id);
}

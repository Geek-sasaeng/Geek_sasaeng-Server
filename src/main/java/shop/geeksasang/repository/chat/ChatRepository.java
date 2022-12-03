package shop.geeksasang.repository.chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.Chat;

import java.awt.print.Book;
import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends MongoRepository<Chat, String> {

    @Query(" {'id' : :#{#id}, 'status' : 'ACTIVE'}")
    Optional<Chat> findByChatId(@Param("id") String id);
//    @Query("{'author' : :#{#author}, 'category' : :#{#category}}")
//    List<Book> findNamedParameters(@Param("author") String author, @Param("category") String category);
}

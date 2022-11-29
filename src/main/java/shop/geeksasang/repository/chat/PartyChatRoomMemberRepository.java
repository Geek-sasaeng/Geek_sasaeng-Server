package shop.geeksasang.repository.chat;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.PartyChatRoomMember;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartyChatRoomMemberRepository extends MongoRepository<PartyChatRoomMember, String> {


    @Query(value = "{ 'memberId' : ?0 , 'chatRoomId': ?1 , 'status' : 'ACTIVE'}")
    Optional<PartyChatRoomMember> findByMemberIdAndChatRoomId(int memberId, String chatRoomId);


    //TODO STATUS 추가해야함
    @Query(value = "{ 'memberId' : ?0 }")
    List<PartyChatRoomMember> findPartyChatRoomMemberByMemberId(int memberId);
}

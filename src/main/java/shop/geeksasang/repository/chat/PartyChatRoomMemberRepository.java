package shop.geeksasang.repository.chat;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.PartyChatRoomMember;


import java.util.List;
import java.util.Optional;

@Repository
public interface PartyChatRoomMemberRepository extends MongoRepository<PartyChatRoomMember, String> {

    @Query("{ 'memberId' : ?0 , 'partyChatRoom': ?1 , 'status' : 'ACTIVE'}")
    Optional<PartyChatRoomMember> findByMemberIdAndChatRoomId(int memberId, ObjectId chatRoomId);

    @Query(value = "{ '_id' : ?0 , 'partyChatRoom': ?1 , 'status' : 'ACTIVE'}")
    Optional<PartyChatRoomMember> findByIdAndChatRoomId(ObjectId memberId, ObjectId  chatRoomId);

    //TODO STATUS 추가해야함
    @Query(value = "{ 'memberId' : ?0 }")
    Slice<PartyChatRoomMember> findPartyChatRoomMemberByMemberId(int memberId, Pageable pageable);

    @Query("{'_id': ?0 , 'partyChatRoom': ?1 }")
    @Update("{ $set : { 'isRemittance': true } }")
    void changeRemittance(ObjectId memberId, ObjectId roomId);

    @Query("{'partyChatRoom' : ?0, '_id' : { $ne : ?1} , 'status' : 'ACTIVE'}")
    List<PartyChatRoomMember> findByChatRoomIdAndIdNotEqual(ObjectId chatRoomId, ObjectId chiefId);

}
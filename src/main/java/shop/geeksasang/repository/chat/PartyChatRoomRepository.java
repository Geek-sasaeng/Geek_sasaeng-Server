package shop.geeksasang.repository.chat;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.Update;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import shop.geeksasang.domain.chat.PartyChatRoom;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PartyChatRoomRepository extends MongoRepository<PartyChatRoom, String> {
    @Query("{ '_id': ?0, 'status' : 'ACTIVE'}") // 0번째 파라미터 조건
    Optional<PartyChatRoom> findByPartyChatRoomId(ObjectId id);
    @Query("{ '_id': ?0, 'status' : 'ACTIVE', 'isFinish' : true}") // 0번째 파라미터 조건
    Optional<PartyChatRoom> findByPartyChatRoomIdAndIsFinish(ObjectId id);

    @Query("{ chief: ?0, 'status' : 'ACTIVE'}")
    Optional<PartyChatRoom> findPartyChatRoomByChiefId(ObjectId chiefId);


    @Query("{ _id : ?0 }")
    //@Update("{ $pull: { 'participants': { _id : ?1 } } }")
    @Update("{ $pull : { 'participants': { $in : [?1] } } }")
    void deleteParticipant(ObjectId roomId, ObjectId participantId);

    @Query("{'deliveryPartyId' : :#{#deliveryPartyId}, 'status' : 'ACTIVE'}")
    Optional<PartyChatRoom> findByDeliveryPartyId(@Param("deliveryPartyId") int deliveryPartyId);

    @Query("{ '_id' : ?0 }")
    @Update("{ $set : { 'isFinish' : true }}")
    void changeIsFinish(ObjectId partyChatRoomId);

    @Query("{ '_id' :  ?0 }")
    @Update("{ $set : { 'orderStatus' : 'ORDER_COMPLETE' }}")
    void changeOrderStatusToOrderComplete(ObjectId chatRoomId);

    @Query("{ '_id' :  ?0 }")
    @Update("{ $set : { 'orderStatus' : 'DELIVERY_COMPLETE' }}")
    void changeOrderStatusToDeliveryComplete(ObjectId chatRoomId);

    @Query("{ '_id' : ?0 }")
    @Update("{ $set : { 'lastChatAt' : ?1 }}")
    void changeLastChatAt(ObjectId partyChatRoomId, LocalDateTime lastChatAt);

    @Aggregation(pipeline ={
            "{ $unwind : '$participants' } ",
            "{ $lookup: { from : 'partyChatRoomMember', localField: 'participants', foreignField: '_id' , as : 'member' }} ",
            "{ $match : { $and: [ { 'member.memberId' : { $eq : ?0 }} , { 'member.status' : { $eq : 'ACTIVE' }} ]}}"
    })
    Slice<PartyChatRoom> findByParticipantsIn(int memberId, Pageable pageable);



    @Query("{ '_id' : ?0 }")
    @Update("{ $set : { 'maxMatching' : ?1 }}")
    void updateMaxNumber(ObjectId partyChatRoomId, int maxNumber);
//    @Aggregation(pipeline ={
//            "{ $unwind : '$participants' } ",
//            "{ $lookup: { from : 'partyChatRoomMember', localField: 'participants', foreignField: '_id' , as : 'member' }} ",
//            "{ $match : { $and: [ { 'member.memberId' : { $eq : ?0 }} , { 'member.status' : { $eq : 'ACTIVE' }} ]}}"
//    })
//    List<PartyChatRoom> findByParticipantsIn(int memberId);
}

package shop.geeksasang.service.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.chat.Chat;
import shop.geeksasang.domain.chat.ChatRoom;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.dto.chat.PostChattingRes;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;
import shop.geeksasang.rabbitmq.MQController;
import shop.geeksasang.repository.chat.ChatRepository;
import shop.geeksasang.repository.chat.ChatRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryPartyChatService {

    private final ChatRoomRepository ChatRoomRepository;
    private final ChatRepository chattingRepository;
    private final PartyChatRoomRepository partyChatRoomRepository;
    private final MQController mqController;

    @Transactional(readOnly = false)
    public String createChatRoom(int userId, String title){
        List<Chat> chattings = new ArrayList<>();
        List<PartyChatRoomMember> participants = new ArrayList<>();
        PartyChatRoom ChatRoom = new PartyChatRoom(title, chattings, participants, "123", "국민", "Delivery", false, 5);
        PartyChatRoom saveChatRoom = partyChatRoomRepository.save(ChatRoom);
        return saveChatRoom.getId();
    }

    @Transactional(readOnly = false)
    public void createChatting(int userId, String email, String ChatRoomId, String content) {
        // mongoDB 채팅 저장
        Chat chatting = new Chat(content);
        Chat saveChatting = chattingRepository.save(chatting);

        // json 형식으로 변환 후 RabbitMQ 전송
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        PostChattingRes postChattingRes = new PostChattingRes(email, ChatRoomId, saveChatting.getContent(), saveChatting.getBaseEntityMongo().getCreatedAt()); // ObjectMapper가 java8의 LocalDateTime을 지원하지 않는 에러 해결
        String saveChattingJsonStr = null;
        try {
            saveChattingJsonStr = mapper.writeValueAsString(postChattingRes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mqController.sendMessage(saveChattingJsonStr, ChatRoomId); // rabbitMQ 메시지 publish
    }

    @Transactional(readOnly = false)
    public void joinPartyChatRoom(String ChatRoomId, LocalDateTime enterTime, boolean isRemittance, Long memberId){

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(ChatRoomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_CHATTING_ROOM));

        // 파티 입장하는 멤버 정보 추가
        PartyChatRoomMember partyChatRoomMember = new PartyChatRoomMember(LocalDateTime.now(), isRemittance, memberId);

        partyChatRoom.changeParticipants(partyChatRoomMember);
        partyChatRoomRepository.save(partyChatRoom); // MongoDB는 JPA처럼 변경감지가 안되어서 직접 저장해줘야 한다.
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> findAllPartyChatRooms(int userId){
//        List<String> partyChatRoomList = ChatRoomRepository.findAll().stream()
//                .map(ChatRoom -> ChatRoom.getId())
//                .collect(Collectors.toList());
        List<ChatRoom> partyChatRoomList = ChatRoomRepository.findAll();

        return partyChatRoomList;
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> findPartyChatRoom(int userId, String partyChatRoomId){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("id").is(partyChatRoomId));
        List<ChatRoom> partyChatRoomList = ChatRoomRepository.findAllByPartyChatRoomId(partyChatRoomId);

        return partyChatRoomList;
    }

    @Transactional(readOnly = true)
    public List<Chat> findPartyChattings(int userId, String partyChatRoomId){
        List<Chat> chattingList = chattingRepository.findAll();


        return chattingList;
    }


}
// String exchange, String routingKey, Object message
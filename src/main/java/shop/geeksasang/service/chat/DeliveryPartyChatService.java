package shop.geeksasang.service.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.controller.chat.DeliveryPartyChatController;
import shop.geeksasang.domain.chat.Chat;
import shop.geeksasang.domain.chat.ChatRoom;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.dto.chat.GetPartyChattingRoomsReq;
import shop.geeksasang.dto.chat.PostChatRes;
import shop.geeksasang.repository.chat.PartyChatRoomMemberRepository;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;
import shop.geeksasang.rabbitmq.MQController;
import shop.geeksasang.repository.chat.ChatRepository;
import shop.geeksasang.repository.chat.ChatRoomRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryPartyChatService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRepository chatRepository;
    private final PartyChatRoomRepository partyChatRoomRepository;
    private final MQController mqController;
    private final PartyChatRoomMemberRepository partyChatRoomMemberRepository;

    private static final String PAGING_STANDARD = "createdAt";

    @Transactional(readOnly = false)
    public String createChatRoom(int memberId, String title){
        List<Chat> chattings = new ArrayList<>();
        List<PartyChatRoomMember> participants = new ArrayList<>();
        PartyChatRoom ChatRoom = new PartyChatRoom(title, chattings, participants, "123", "국민", "Delivery", false, 5);
        PartyChatRoom saveChatRoom = partyChatRoomRepository.save(ChatRoom);
        return saveChatRoom.getId();
    }

    @Transactional(readOnly = false)
    public void createChat(int memberId, String email, String chatRoomId, String content, Boolean isSystemMessage, String profileImgUrl) {

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(chatRoomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_CHATTING_ROOM));

        PartyChatRoomMember partyChatRoomMember = partyChatRoomMemberRepository.findByMemberIdAndChatRoomId(memberId, chatRoomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTYCHATROOM_MEMBER));

        List<Integer> readMembers = new ArrayList<>();

        // mongoDB 채팅 저장
        Chat chat = new Chat(content, partyChatRoom, isSystemMessage, partyChatRoomMember, profileImgUrl, readMembers);
        Chat saveChat = chatRepository.save(chat);

        // json 형식으로 변환 후 RabbitMQ 전송
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        PostChatRes postChatRes = PostChatRes.toDto(saveChat, email);
        String saveChatJson = null;
        try {
            saveChatJson = mapper.writeValueAsString(postChatRes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mqController.sendMessage(saveChatJson, chatRoomId); // rabbitMQ 메시지 publish
    }

    @Transactional(readOnly = false)
    public void joinPartyChatRoom(String ChatRoomId, LocalDateTime enterTime, boolean isRemittance, int memberId){

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(ChatRoomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_CHATTING_ROOM));

        // 파티 입장하는 멤버 정보 추가
        PartyChatRoomMember partyChatRoomMember = new PartyChatRoomMember(LocalDateTime.now(), isRemittance, memberId);

        partyChatRoom.changeParticipants(partyChatRoomMember);
        partyChatRoomMemberRepository.save(partyChatRoomMember);
        partyChatRoomRepository.save(partyChatRoom); // MongoDB는 JPA처럼 변경감지가 안되어서 직접 저장해줘야 한다.
    }

    @Transactional(readOnly = true)
    public GetPartyChattingRoomsReq findPartyChatRooms(int memberId, int cursor){

        PageRequest page = PageRequest.of(cursor, 10, Sort.by(Sort.Direction.ASC, PAGING_STANDARD));
        Slice<PartyChatRoomMember> members = partyChatRoomMemberRepository.findPartyChatRoomMemberByMemberId(memberId, page);
        List<PartyChatRoom> result = members.stream()
                .map(member -> member.getPartyChatRoom())
                .collect(Collectors.toList());
        return new GetPartyChattingRoomsReq(result, members.isLast());


        //return null;
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> findPartyChatRoom(int memberId, String partyChatRoomId){
//        Query query = new Query();
//        query.addCriteria(Criteria.where("id").is(partyChatRoomId));


        List<ChatRoom> partyChatRoomList = chatRoomRepository.findAllByPartyChatRoomId(partyChatRoomId);

        return partyChatRoomList;
    }

    @Transactional(readOnly = true)
    public List<Chat> findPartyChattings(int memberId, String partyChatRoomId){
        List<Chat> chattingList = chatRepository.findAll();
        return chattingList;
    }


}
// String exchange, String routingKey, Object message
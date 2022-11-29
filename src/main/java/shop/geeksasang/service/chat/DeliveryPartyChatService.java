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
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.chat.PostChatRes;
import shop.geeksasang.dto.chat.chatmember.PartyChatRoomMemberRes;
import shop.geeksasang.dto.chat.partychatroom.PartyChatRoomRes;
import shop.geeksasang.repository.chat.PartyChatRoomMemberRepository;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;
import shop.geeksasang.rabbitmq.MQController;
import shop.geeksasang.repository.chat.ChatRepository;
import shop.geeksasang.repository.chat.ChatRoomRepository;
import shop.geeksasang.repository.member.MemberRepository;

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
    private final MemberRepository memberRepository;

    @Transactional(readOnly = false)
    public PartyChatRoomRes createChatRoom(int memberId, String title, String accountNumber, String bank, String category, Integer maxMatching){

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_USER));

        List<Chat> chattings = new ArrayList<>();
        List<PartyChatRoomMember> participants = new ArrayList<>();
        PartyChatRoom chatRoom = new PartyChatRoom(title, chattings, participants, accountNumber, bank, category, false, maxMatching);
        partyChatRoomRepository.save(chatRoom);

        //rabbitMQ 채팅방 생성 요청
        try{
            mqController.createChatRoom(member.getEmail().toString(), chatRoom.getId());
        }catch (Exception e){
            System.out.println("mqController에서 채팅방 생성 에러 발생");
        }

        PartyChatRoomRes res = PartyChatRoomRes.toDto(chatRoom);
        return res;
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
    public PartyChatRoomMemberRes joinPartyChatRoom(int memberId, String chatRoomId, LocalDateTime enterTime){

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXIST_USER));

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(chatRoomId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_CHATTING_ROOM));

        //Validation 기존의 멤버인지 예외 처리
        if(partyChatRoom.getParticipants().stream().anyMatch(participant -> participant.getMemberId() == memberId)){
            throw new BaseException(BaseResponseStatus.ALREADY_PARTICIPATE_CHATROOM);
        }

        PartyChatRoomMember partyChatRoomMember = new PartyChatRoomMember(memberId, LocalDateTime.now(), false, partyChatRoom, member.getEmail().toString());
        partyChatRoomMemberRepository.save(partyChatRoomMember);

        partyChatRoom.changeParticipants(partyChatRoomMember);
        partyChatRoomRepository.save(partyChatRoom); // MongoDB는 JPA처럼 변경감지가 안되어서 직접 저장해줘야 한다.

        mqController.joinChatRoom(member.getEmail().toString(), partyChatRoom.getId());         // rabbitmq 큐 생성 및 채팅방 exchange와 바인딩

        PartyChatRoomMemberRes res = PartyChatRoomMemberRes.toDto(partyChatRoomMember, partyChatRoom);
        return res;
    }

    @Transactional(readOnly = true)
    public List<ChatRoom> findAllPartyChatRooms(int memberId){
//        List<String> partyChatRoomList = chatRoomRepository.findAll().stream()
//                .map(ChatRoom -> ChatRoom.getId())
//                .collect(Collectors.toList());
        List<ChatRoom> partyChatRoomList = chatRoomRepository.findAll();

        return partyChatRoomList;
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
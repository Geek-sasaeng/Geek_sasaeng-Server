package shop.geeksasang.service.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.domain.chat.Chat;
import shop.geeksasang.domain.chat.ChatRoom;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.dto.chat.chatchief.PostRemoveMemberByChiefReq;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomRes;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomsRes;
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

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

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

    private static final String PAGING_STANDARD = "createdAt";

    @Transactional(readOnly = false)
    public PartyChatRoomRes createChatRoom(int memberId, String title, String accountNumber, String bank, String category, Integer maxMatching){

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        List<Chat> chats = new ArrayList<>();
        List<PartyChatRoomMember> participants = new ArrayList<>();
        PartyChatRoomMember chief = new PartyChatRoomMember(LocalDateTime.now(), false, memberId);
        PartyChatRoom chatRoom = new PartyChatRoom(title, chats, participants, accountNumber, bank, category, false, maxMatching, chief);
        chatRoom.addParticipants(chief);

        partyChatRoomRepository.save(chatRoom); //초기 생성

        chief.enterRoom(chatRoom);
        partyChatRoomMemberRepository.save(chief);
        partyChatRoomRepository.save(chatRoom); //방장 업데이트
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
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHATTING_ROOM));

        PartyChatRoomMember partyChatRoomMember = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(partyChatRoom.getId()))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

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
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(chatRoomId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHATTING_ROOM));

        //Validation 기존의 멤버인지 예외 처리
        if(partyChatRoom.getParticipants().stream().anyMatch(participant -> participant.getMemberId() == memberId)){
            throw new BaseException(ALREADY_PARTICIPATE_CHATROOM);
        }

        PartyChatRoomMember partyChatRoomMember = new PartyChatRoomMember(memberId, LocalDateTime.now(), false, partyChatRoom, member.getEmail().toString());
        partyChatRoomMemberRepository.save(partyChatRoomMember);

        partyChatRoom.addParticipants(partyChatRoomMember);
        partyChatRoomRepository.save(partyChatRoom); // MongoDB는 JPA처럼 변경감지가 안되어서 직접 저장해줘야 한다.

        mqController.joinChatRoom(member.getEmail().toString(), partyChatRoom.getId());         // rabbitmq 큐 생성 및 채팅방 exchange와 바인딩

        PartyChatRoomMemberRes res = PartyChatRoomMemberRes.toDto(partyChatRoomMember, partyChatRoom);
        return res;
    }

    @Transactional(readOnly = true)
    public GetPartyChatRoomsRes findPartyChatRooms(int memberId, int cursor){

        PageRequest page = PageRequest.of(cursor, 10, Sort.by(Sort.Direction.ASC, PAGING_STANDARD));
        Slice<PartyChatRoomMember> members = partyChatRoomMemberRepository.findPartyChatRoomMemberByMemberId(memberId, page);
        List<GetPartyChatRoomRes> result= members.stream()
                .map(member -> GetPartyChatRoomRes.of(member.getPartyChatRoom()))
                .collect(Collectors.toList());

        return new GetPartyChatRoomsRes(result, members.isLast());
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


    //TODO 몽고 트랜잭션 매니저를 달아야하는데, 트랜잭션 매니저 달면 JPA랑 충돌해서 문제가 일어나는듯
    public void removeMemberByChief(int chiefId, PostRemoveMemberByChiefReq dto) {
        PartyChatRoomMember chief = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(chiefId, new ObjectId(dto.getRoomId()))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        PartyChatRoomMember removedMember = partyChatRoomMemberRepository
                .findByIdAndChatRoomId(new ObjectId(dto.getRemovedMemberId()), new ObjectId(dto.getRoomId()))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        PartyChatRoom chatRoom = partyChatRoomRepository.findPartyChatRoomByChiefId(new ObjectId(chief.getId()))
                .orElseThrow(() -> new BaseException(NOT_EXIST_CHAT_ROOM_CHIEF));

        if(chatRoom.isNotChief(chief)){
            throw new BaseException(NOT_CHAT_ROOM_CHIEF);
        }

        if(removedMember.alreadyRemit()){
            throw new BaseException(CANT_REMOVE_REMIT_MEMBER);
        }

        chatRoom.removeParticipant(removedMember);
        removedMember.delete();
        partyChatRoomRepository.save(chatRoom);
        partyChatRoomMemberRepository.save(removedMember);
    }
}
// String exchange, String routingKey, Object message
package shop.geeksasang.service.chat;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.status.OrderStatus;
import shop.geeksasang.domain.chat.Chat;
import shop.geeksasang.domain.chat.PartyChatRoomMember;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.member.Member;

import shop.geeksasang.dto.chat.chatchief.DeleteMemberByChiefReq;

import shop.geeksasang.dto.chat.PostChatImageRes;

import shop.geeksasang.dto.chat.chatmember.GetPartyChatRoomMembersInfoRes;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomDetailRes;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomRes;
import shop.geeksasang.dto.chat.partychatroom.GetPartyChatRoomsRes;
import shop.geeksasang.dto.chat.PostChatRes;
import shop.geeksasang.dto.chat.chatmember.PartyChatRoomMemberRes;
import shop.geeksasang.dto.chat.partychatroom.PartyChatRoomRes;
import shop.geeksasang.repository.chat.PartyChatRoomMemberRepository;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;
import shop.geeksasang.rabbitmq.MQController;
import shop.geeksasang.repository.chat.ChatRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyRepository;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.service.common.AwsS3Service;
import shop.geeksasang.service.deliveryparty.DeliveryPartyMemberService;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DeliveryPartyChatService {

    private final ChatRepository chatRepository;
    private final PartyChatRoomRepository partyChatRoomRepository;
    private final MQController mqController;
    private final PartyChatRoomMemberRepository partyChatRoomMemberRepository;
    private final MemberRepository memberRepository;
    private final AwsS3Service awsS3Service;
    private final DeliveryPartyMemberService deliveryPartyMemberService;
    private final DeliveryPartyRepository deliveryPartyRepository;

    private final ObjectMapper objectMapper;

    private static final String PAGING_STANDARD = "lastChatAt";

    @Transactional(readOnly = false)
    public PartyChatRoomRes createChatRoom(int memberId, String title, String accountNumber, String bank, String category, Integer maxMatching, int deliveryPartyId){

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        String email = member.getEmail().getAddress();
        List<Chat> chats = new ArrayList<>();
        List<PartyChatRoomMember> participants = new ArrayList<>();
        PartyChatRoomMember chief = new PartyChatRoomMember(LocalDateTime.now(), false, memberId);
        PartyChatRoom chatRoom = new PartyChatRoom(title, chats, participants, accountNumber, bank, category, false, maxMatching, chief, deliveryPartyId, LocalDateTime.now());
        chatRoom.addParticipants(chief);

        partyChatRoomRepository.save(chatRoom); //초기 생성

        chief.enterRoom(chatRoom);
        partyChatRoomMemberRepository.save(chief);
        partyChatRoomRepository.save(chatRoom); //방장 업데이트
        //rabbitMQ 채팅방 생성 요청
        try {
            mqController.createChatRoom(email, chatRoom.getId());
            mqController.joinChatRoom(memberId, chatRoom.getId());
        } catch (Exception e) {
            System.out.println("mqController에서 채팅방 생성 에러 발생");
        }

        PartyChatRoomRes res = PartyChatRoomRes.toDto(chatRoom);
        return res;
    }

    @Transactional(readOnly = false)
    public void createChat(int memberId, String chatRoomId, String content, Boolean isSystemMessage, String profileImgUrl, String chatType, String chatId, Boolean isImageMessage) {

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(new ObjectId(chatRoomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        PartyChatRoomMember partyChatRoomMember = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(partyChatRoom.getId()))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        List<Integer> readMembers = new ArrayList<>();

        // mongoDB 채팅 저장
        Chat chat = null;
        if (chatType.equals("publish")) {
            chat = new Chat(content, partyChatRoom, isSystemMessage, partyChatRoomMember, profileImgUrl, readMembers);
            partyChatRoomRepository.changeLastChatAt(new ObjectId(partyChatRoom.getId()), LocalDateTime.now());
        } else if (chatType.equals("read")) {
            chat = chatRepository.findByChatId(new ObjectId(chatId)).orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT));
        }

        chat.addReadMember(memberId);// 읽은 멤버 추가
        Chat saveChat = chatRepository.save(chat);

        int unreadMemberCnt = saveChat.getUnreadMemberCnt(); // 안읽은 멤버 수 계산

        // json 형식으로 변환 후 RabbitMQ 전송
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        PostChatRes postChatRes = PostChatRes.toDto(saveChat, chatType, unreadMemberCnt, member);
        String saveChatJson = null;
        try {
            saveChatJson = mapper.writeValueAsString(postChatRes);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        mqController.sendMessage(saveChatJson, chatRoomId); // rabbitMQ 메시지 publish
    }


    @Transactional(readOnly = false)
    public void createChatImage(int memberId, String chatRoomId, String content, Boolean isSystemMessage, String chatType, String chatId, List<MultipartFile> images, Boolean isImageMessage) {

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        String profileImgUrl = member.getProfileImgUrl();

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(new ObjectId(chatRoomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        PartyChatRoomMember partyChatRoomMember = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(chatRoomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        List<Integer> readMembers = new ArrayList<>();

        if (images.size() > 5) {//Validation: 이미지 갯수 5 제한
            throw new BaseException(EXCEEDED_IMAGE);
        }

        // - mongo 서버에서 이미지 aws S3에 저장 후
        //- s3 이미지 url을 mongoDB 채팅에 저장
        List<String> imgUrls = null;
        try {
            for (MultipartFile image : images) {
                String imgUrl = awsS3Service.upload(image.getInputStream(), image.getOriginalFilename(), image.getSize());

                // mongoDB 채팅 저장
                Chat chat = null;
                if (chatType.equals("publish")) {
                    chat = new Chat(imgUrl, partyChatRoom, isSystemMessage, partyChatRoomMember, profileImgUrl, readMembers, isImageMessage);
                } else if (chatType.equals("read")) {
                    chat = chatRepository.findByChatId(new ObjectId(chatId)).orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT));
                }

                chat.addReadMember(memberId);// 읽은 멤버 추가
                Chat saveChat = chatRepository.save(chat);

                int unreadMemberCnt = saveChat.getUnreadMemberCnt(); // 안읽은 멤버 수 계산

                // json 형식으로 변환 후 RabbitMQ 전송
                ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
                PostChatImageRes res = PostChatImageRes.toDto(saveChat, member.getNickName(), chatType, unreadMemberCnt);
                String saveChatJson = null;
                try {
                    saveChatJson = mapper.writeValueAsString(res);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                mqController.sendMessage(saveChatJson, chatRoomId); // rabbitMQ 메시지 publish
            }
        } catch (IOException ioException) {
            System.out.println("채팅 이미지 aws s3 업로드에 실패하였습니다.");
            ioException.printStackTrace();
        }
    }

    @Transactional(readOnly = false)
    public PartyChatRoomMemberRes joinPartyChatRoom(int memberId, String chatRoomId, LocalDateTime enterTime) {

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        String profileImgUrl = member.getProfileImgUrl();

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(new ObjectId(chatRoomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        //이미 마감했는지
        if(partyChatRoom.getIsFinish()){
            throw new BaseException(ALREADY_PARTY_FINISH);
        }

        //Validation 기존의 멤버인지 예외 처리
        if (partyChatRoom.getParticipants().stream().anyMatch(participant -> participant.getMemberId() == memberId)) {
            throw new BaseException(ALREADY_PARTICIPATE_CHATROOM);
        }

        //파티의 현재 인원 수와 maxmatching이 같을 경우
        if(partyChatRoom.getMaxMatching() == partyChatRoom.getParticipants().size()){
            throw new BaseException(ALREADY_PARTY_FINISH);
        }

        PartyChatRoomMember partyChatRoomMember = new PartyChatRoomMember(memberId, LocalDateTime.now(), false, partyChatRoom, member.getEmail().toString());
        partyChatRoomMemberRepository.save(partyChatRoomMember);

        partyChatRoom.addParticipants(partyChatRoomMember);

        mqController.joinChatRoom(member.getId(), partyChatRoom.getId());         // rabbitmq 큐 생성 및 채팅방 exchange와 바인딩

        // 입장 시스템 메시지 전송
        this.createChat(memberId, chatRoomId, member.getNickName()+"님이 입장하였습니다.", true, profileImgUrl, "publish", "none", false);


        // participants의 개수와 maxMatching이 같아지면 isFinish = true;
        if(partyChatRoom.getMaxMatching() == partyChatRoom.getParticipants().size()){
            partyChatRoom.changeIsFinishToTrue();
            this.createChat(memberId, chatRoomId, "모든 파티원이 입장을 마쳤네요! 메뉴를 정해보세요 :)", true, profileImgUrl, "publish", "none", false);
        }

        partyChatRoomRepository.save(partyChatRoom); // MongoDB는 JPA처럼 변경감지가 안되어서 직접 저장해줘야 한다.

        PartyChatRoomMemberRes res = PartyChatRoomMemberRes.toDto(partyChatRoomMember, partyChatRoom);
        return res;
    }

    @Transactional(readOnly = true)
    public GetPartyChatRoomsRes findPartyChatRooms(int memberId, int cursor) {

        PageRequest page = PageRequest.of(cursor, 10, Sort.by(Sort.Direction.DESC, PAGING_STANDARD));
        Slice<PartyChatRoom> chatRooms = partyChatRoomRepository.findByParticipantsIn(memberId, page);
        List<GetPartyChatRoomRes> result = chatRooms.stream()
                .map(partyChatRoom -> GetPartyChatRoomRes.from(partyChatRoom, memberId))
                .collect(Collectors.toList());

        return new GetPartyChatRoomsRes(result, chatRooms.isLast());
    }


    @Transactional(readOnly = true)
    public List<Chat> findPartyChattings(int memberId, String partyChatRoomId) {
        List<Chat> chattingList = chatRepository.findAll();
        return chattingList;
    }


    //TODO 몽고 트랜잭션 매니저를 달아야하는데, 트랜잭션 매니저 달면 JPA랑 충돌해서 문제가 일어나는듯
    @Transactional(readOnly = false)
    public void removeMemberByChief(int chiefId, DeleteMemberByChiefReq dto) {
        PartyChatRoomMember chief = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(chiefId, new ObjectId(dto.getRoomId()))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));


        dto.getRemovedChatMemberIdList()
                .forEach(id -> {
                    removeMember(chiefId, dto, chief, id);
                });

    }

    @Transactional(readOnly = false)
    public void removeMember(int chiefId, DeleteMemberByChiefReq dto, PartyChatRoomMember chief, String id) {
        PartyChatRoomMember removedMember = partyChatRoomMemberRepository
                .findByIdAndChatRoomId(new ObjectId(id), new ObjectId(dto.getRoomId()))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        PartyChatRoom chatRoom = partyChatRoomRepository.findPartyChatRoomByChiefId(new ObjectId(chief.getId()))
                .orElseThrow(() -> new BaseException(NOT_EXIST_CHAT_ROOM_CHIEF));

        if (chatRoom.isNotChief(chief)) {
            throw new BaseException(NOT_CHAT_ROOM_CHIEF);
        }

        if (removedMember.alreadyRemit()) {
            throw new BaseException(CANT_REMOVE_REMIT_MEMBER);
        }

        chatRoom.removeParticipant(removedMember);
        partyChatRoomRepository.deleteParticipant(new ObjectId(chatRoom.getId()), new ObjectId(removedMember.getId()));
        //partyChatRoomRepository.save(chatRoom);
        partyChatRoomMemberRepository.save(removedMember);

        String nickName = memberRepository.findMemberById(removedMember.getMemberId())
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER)).getNickName();

        this.createChat(chiefId, dto.getRoomId(), nickName + "파티장의 강제 퇴장 요청으로 인해 " + nickName + "이 퇴장 처리되었습니다"
                , true, null, "publish", "none", false);
    }

    public void changeChief(int chiefId, String roomId) throws JsonProcessingException {
        PartyChatRoomMember chief = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(chiefId, new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        PartyChatRoom chatRoom = partyChatRoomRepository.findPartyChatRoomByChiefId(new ObjectId(chief.getId()))
                .orElseThrow(() -> new BaseException(NOT_EXIST_CHAT_ROOM_CHIEF));

        //송금한 사람이 존재한다면 거절
        if (chatRoom.existAlreadyRemittanceParticipant()) {
            throw new RuntimeException("이미 송금한 사람이 있습니다");
        }

        //방장만 존재한다면 삭제
        if (chatRoom.isOnlyChief()) {
            //방장 지우고, 채팅방도 삭제
            chief.delete();
            chatRoom.removeParticipant(chief);
            chatRoom.deleteChief();
            partyChatRoomRepository.save(chatRoom);
            partyChatRoomRepository.deleteParticipant(new ObjectId(chatRoom.getId()), new ObjectId(chief.getId()));
            partyChatRoomMemberRepository.save(chief);
            return;
        };

        //이미 송금한 사람이 없다고 검사함.방장을 빼고 첫 번째 사람을 가져와서 방장으로 임명해야함.
        //chatRoom.removeParticipant(chief);

        PartyChatRoomMember changeChief = chatRoom.changeChief();
        chief.delete();

        partyChatRoomRepository.save(chatRoom);
        partyChatRoomMemberRepository.save(chief);
        partyChatRoomMemberRepository.save(changeChief);

        Member member = memberRepository.findMemberById(changeChief.getMemberId())
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        String nickName = member.getNickName();

        //시스템 메시지
        Chat chat = new Chat("방장의 활동 중단에 따라 새로운 방장으로 '" + nickName + "'님이 선정되었어요", chatRoom, true, null, null, new ArrayList<>());
        chat.addReadMember(changeChief.getMemberId());// 읽은 멤버 추가
        Chat saveChat = chatRepository.save(chat);
        int unreadMemberCnt = saveChat.getUnreadMemberCnt();


        // json 형식으로 변환 후 RabbitMQ 전송
        ObjectMapper mapper = objectMapper.registerModule(new JavaTimeModule());
        PostChatRes postChatRes = PostChatRes.toDto(saveChat, "publish", unreadMemberCnt, member);
        String saveChatJson = mapper.writeValueAsString(postChatRes);
        mqController.sendMessage(saveChatJson, roomId); // rabbitMQ 메시지 publish
        partyChatRoomRepository.changeLastChatAt(new ObjectId(chatRoom.getId()), LocalDateTime.now()); //TODO: 시간이 맞는지 테스트 해봐야 함

    }

    public void removeMember(int memberId, String roomId) throws JsonProcessingException {
        PartyChatRoomMember chatRoomMember = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        partyChatRoomRepository.deleteParticipant(new ObjectId(roomId), new ObjectId(chatRoomMember.getId()));


        chatRoomMember.delete();
        partyChatRoomMemberRepository.save(chatRoomMember);

        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        //시스템 메시지
        Chat chat = new Chat(member.getNickName() + "님이 퇴장했습니다.", partyChatRoom, true, null, null, new ArrayList<>());
        chat.addReadMember(memberId);// 읽은 멤버 추가
        Chat saveChat = chatRepository.save(chat);
        int unreadMemberCnt = saveChat.getUnreadMemberCnt(); // 안읽은 멤버 수 계산

        // json 형식으로 변환 후 RabbitMQ 전송
        ObjectMapper mapper = new ObjectMapper().registerModule(new JavaTimeModule());
        PostChatRes postChatRes = PostChatRes.toDto(saveChat, "publish", unreadMemberCnt, member);
        String saveChatJson = mapper.writeValueAsString(postChatRes);
        mqController.sendMessage(saveChatJson, roomId); // rabbitMQ 메시지 publish
        partyChatRoomRepository.changeLastChatAt(new ObjectId(partyChatRoom.getId()), LocalDateTime.now()); //TODO: 시간이 맞는지 테스트 해봐야 함

    }

    @Transactional(readOnly = false)
    public void changeRemittance(int memberId, String roomId) {

        //회원 존재 여부 확인
        PartyChatRoomMember member = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        //채팅방 존재 여부 확인
        PartyChatRoom chatRoom = partyChatRoomRepository.findByPartyChatRoomId(new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        //mongo 데이터 수정
        partyChatRoomMemberRepository.changeRemittance(new ObjectId(member.getId()), new ObjectId(roomId));

        //mysql 데이터 수정
        deliveryPartyMemberService.changeAccountTransferStatus(chatRoom.getDeliveryPartyId(), member.getMemberId());
    }

    @Transactional(readOnly = false)
    public void changeOrderStatus(int memberId, String roomId) {

        //mongo(채팅방) 회원 존재 여부 확인
        PartyChatRoomMember chatRoomMember = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        //mysql 회원 존재 여부 확인
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        //채팅방 존재 여부 확인
        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomIdAndIsFinish(new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_FINISH_CHAT_ROOM));

        //배달파티 조회
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndMatchingStatus(partyChatRoom.getDeliveryPartyId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_MATCHING_FINISH_PARTY));

        //mysql - 주문 완료 상태 수정
        deliveryParty.changeOrderStatusToOrderComplete();

        //mongo - OrderStatus 값 바꾸기
        partyChatRoomRepository.changeOrderStatusToOrderComplete(new ObjectId(roomId));

        //주문 완료 시스템 메시지
        this.createChat(memberId, roomId, "주문이 완료되었습니다.", true, member.getProfileImgUrl(), "publish", "none", false);

    }

    @Transactional(readOnly = true)
    public GetPartyChatRoomDetailRes getPartyChatRoomDetailById(String chatRoomId, int memberId){
        //채팅방 조회
        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomId(new ObjectId(chatRoomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        //채팅방 참여 회원 조회
        PartyChatRoomMember member = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(chatRoomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        //요청 보낸 id가 방장인지 확인
        Boolean isChief = partyChatRoom.getChief().getId().equals(member.getId());

        //주문 완료 여부 확인
        Boolean isOrderFinish = partyChatRoom.getOrderStatus().equals(OrderStatus.ORDER_COMPLETE);

        return GetPartyChatRoomDetailRes.toDto(partyChatRoom, member, isChief, isOrderFinish);
    }

    @Transactional(readOnly = false)
    public void changeDeliveryComplete(int memberId, String roomId){

        //mongo(채팅방) 회원 존재 여부 확인
        PartyChatRoomMember chatRoomMember = partyChatRoomMemberRepository
                .findByMemberIdAndChatRoomId(memberId, new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));

        //mysql 회원 존재 여부 확인
        Member member = memberRepository.findMemberById(memberId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        //채팅방 존재 여부 확인
        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByPartyChatRoomIdAndIsFinish(new ObjectId(roomId))
                .orElseThrow(() -> new BaseException(NOT_EXISTS_FINISH_CHAT_ROOM));

        //배달파티 조회
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndMatchingStatus(partyChatRoom.getDeliveryPartyId())
                .orElseThrow(() -> new BaseException(NOT_EXISTS_MATCHING_FINISH_PARTY));

        //mysql - 배달 완료 상태 수정
        deliveryParty.changeOrderStatusToDeliveryComplete();

        //mongo - OrderStatus 값 바꾸기
        partyChatRoomRepository.changeOrderStatusToDeliveryComplete(new ObjectId(roomId));

        String chiefId = partyChatRoom.getChief().getId();
        //방장 여부 확인
        if(!chiefId.equals(chatRoomMember.getId()))
            throw new BaseException(DIFFERENT_CHIEF_ID);
    }

    public List<GetPartyChatRoomMembersInfoRes> getChatRoomMembersInfo(Integer partyId, int userId, String partyUUID) {
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(partyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));
        return deliveryParty.getDeliveryPartyMembers()
                .stream()
                .filter(deliveryParty::isNotChief)
                .filter(deliveryParty::isNotDeleteMember)
                .map(member -> {
                    PartyChatRoomMember chatRoomMember = partyChatRoomMemberRepository
                            .findByMemberIdAndChatRoomId(member.getParticipant().getId(), new ObjectId(partyUUID))
                            .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTYCHATROOM_MEMBER));
                    return GetPartyChatRoomMembersInfoRes.from(member, chatRoomMember);
                })
                .collect(Collectors.toList());
    }
}
// String exchange, String routingKey, Object message
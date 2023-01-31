package shop.geeksasang.service.deliveryparty;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.status.BelongStatus;
import shop.geeksasang.config.status.MatchingStatus;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.chat.PartyChatRoom;
import shop.geeksasang.domain.deliveryparty.DeliveryParty;
import shop.geeksasang.domain.deliveryparty.DeliveryPartyMember;
import shop.geeksasang.domain.deliveryparty.FoodCategory;
import shop.geeksasang.domain.deliveryparty.HashTag;
import shop.geeksasang.domain.member.Member;
import shop.geeksasang.domain.university.Dormitory;
import shop.geeksasang.dto.deliveryParty.get.*;
import shop.geeksasang.dto.deliveryParty.patch.PatchDeliveryPartyMatchingStatusRes;
import shop.geeksasang.dto.deliveryParty.patch.PatchDeliveryPartyStatusRes;
import shop.geeksasang.dto.deliveryParty.patch.PatchLeaveChiefRes;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.*;
import shop.geeksasang.repository.block.BlockRepository;
import shop.geeksasang.repository.chat.PartyChatRoomRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyMemberRepository;
import shop.geeksasang.repository.deliveryparty.query.DeliveryPartyQueryRepository;
import shop.geeksasang.repository.deliveryparty.DeliveryPartyRepository;
import shop.geeksasang.repository.deliveryparty.FoodCategoryRepository;
import shop.geeksasang.repository.member.MemberRepository;
import shop.geeksasang.repository.university.DormitoryRepository;
import shop.geeksasang.service.chat.DeliveryPartyChatService;
import shop.geeksasang.utils.ordertime.OrderTimeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class DeliveryPartyService {

    private final DeliveryPartyRepository deliveryPartyRepository;
    private final DeliveryPartyMemberRepository deliveryPartyMemberRepository;
    private final MemberRepository memberRepository;
    private final DormitoryRepository dormitoryRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final HashTagRepository hashTagRepository;
    private final DeliveryPartyQueryRepository deliveryPartyQueryRepository;
    private final BlockRepository blockRepository;
    private final PartyChatRoomRepository partyChatRoomRepository;
    private final DeliveryPartyChatService deliveryPartyChatService;

    private static final int PAGING_SIZE = 10;
    private static final String PAGING_STANDARD = "orderTime";
    private static final String DELETE_PARTY = "파티를 삭제했습니다.";
    private static final String CHANGE_CHIEF = "기존 방장을 삭제하고 새로운 방장으로 교체했습니다.";

    @Transactional(readOnly = false)
    public PostDeliveryPartyRes registerDeliveryParty(PostDeliveryPartyReq dto, int chiefId, int dormitoryId){

        //파티장 조회
       Member chief = memberRepository.findById(chiefId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

       //비활성화 유저(INACTIVE) 확인
       if(chief.getStatus().equals(BaseStatus.INACTIVE)){
           throw new BaseException(INACTIVE_STATUS);
       }

       //신고 3번이상으로 누적된 회원은 파티를 생성할 수 없음
        if(chief.getStatus().equals(BaseStatus.REPORTED)){
            throw new BaseException(BaseResponseStatus.CAN_NOT_CREATE_PARTY);
        }

        //카테고리
        FoodCategory foodCategory = foodCategoryRepository.findFoodCategoryById(dto.getFoodCategory())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_CATEGORY));

        //기숙사
        Dormitory dormitory = dormitoryRepository.findDormitoryById(dormitoryId)
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_DORMITORY));

        //해시태그 -- 기존 로직 유지
        List<HashTag> hashTagList = new ArrayList<>();

        if(dto.isHashTag()){
            HashTag hashTag = hashTagRepository.findById(1).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_HASHTAG));
            hashTagList.add(hashTag);
        }

        //orderTime 분류화
        OrderTimeCategoryType orderTimeCategory = OrderTimeUtils.selectOrderTime(dto.getOrderTime().getHour());

        // 파티 생성 및 저장. 이렇게 의존성이 많이 발생하는데 더 좋은 방법이 있지 않을까?
        DeliveryParty deliveryParty = DeliveryParty.makeParty(dto, orderTimeCategory, dormitory, foodCategory, chief, hashTagList);

        deliveryParty.addPartyMember(new DeliveryPartyMember(chief, deliveryParty));

        //배달파티 저장
        deliveryPartyRepository.save(deliveryParty);

        return PostDeliveryPartyRes.toDto(deliveryParty); //어차피 영속성이 관리하니 id가 들어가므로 생성한걸 보내줘도 된다. 테스트하기 편하므로 이게 더 좋은 코드
    }

    @Transactional(readOnly = false)
    public PutDeliveryPartyRes updateDeliveryParty(PutDeliveryPartyReq dto, JwtInfo jwtInfo, int dormitoryId, int partyId){
        int chiefId = jwtInfo.getUserId();

        //요청 보낸 사용자 Member 찾기
        int memberId = jwtInfo.getUserId();
        Member findMember = memberRepository.findMemberByIdAndStatus(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyById(partyId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        //요청 보낸 사용자와 파티 chief 비교
        if(!findMember.equals(deliveryParty.getChief())){
            throw new BaseException(BaseResponseStatus.NOT_EXISTS_PERMISSION_UPDATE);
        }

        //파티장
        Member chief = memberRepository.findMemberByIdAndStatus(chiefId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        //기숙사
        Dormitory dormitory = dormitoryRepository.findDormitoryById(dormitoryId)
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_DORMITORY));

        //카테고리
        FoodCategory foodCategory = foodCategoryRepository.findFoodCategoryById(dto.getFoodCategory())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_CATEGORY));

        //해시태그 -- 기존 로직 유지
        List<HashTag> hashTagList = new ArrayList<>();

        if(dto.isHashTag()){
            HashTag hashTag = hashTagRepository.findById(1).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_HASHTAG));
            hashTagList.add(hashTag);
        }
        //orderTime 분류화
        OrderTimeCategoryType orderTimeCategory = OrderTimeUtils.selectOrderTime(dto.getOrderTime().getHour());

        // 파티 수정
        DeliveryParty resDeliveryParty = deliveryParty.updateParty(dto, orderTimeCategory, dormitory, foodCategory, chief, hashTagList);

        return PutDeliveryPartyRes.toDto(resDeliveryParty);

    }

    //배달파티 상세조회:
    public GetDeliveryPartyDetailRes getDeliveryPartyDetailById(int partyId, int memberId){
        BelongStatus belongStatus = BelongStatus.N;
        //사용자 본인 여부
        boolean authorStatus = false;

        //요청 보낸 사용자 Member 찾기
        Member findMember = memberRepository.findById(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

//        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdBeforeOrderTime(partyId, LocalDateTime.now()).
//                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(partyId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        //주문 시간이 현재 시간보다 이전이거나 매칭 상태가 Finish 면 오직 방장만이 배달파티 상세보기를 할 수 있다.
        if((deliveryParty.getOrderTime().isBefore(LocalDateTime.now()) | deliveryParty.getMatchingStatus() == MatchingStatus.FINISH) & deliveryParty.getChief() != findMember){
            throw new BaseException(CHIEF_ONLY_SEE_DELIVERY_PARTY);
        }

        //요청 보낸 사용자와 파티 chief 비교
        if(findMember.equals(deliveryParty.getChief())){
            authorStatus = true;
        }

        //요청 보낸 사용자가 이미 파티멤버인지 조회
        //로직: 요청 사용자 id, partyId -> deliveryPartyMember 에서 같은 partyId와 memberId 같은 멤버 조회
        if(deliveryPartyMemberRepository.findDeliveryPartyMemberByMemberIdAndDeliveryPartyId(memberId, partyId).isPresent()){
            belongStatus = BelongStatus.Y;
        }

        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByDeliveryPartyId(partyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        GetDeliveryPartyDetailRes getDeliveryPartyDetailRes = GetDeliveryPartyDetailRes.toDto(deliveryParty, authorStatus, belongStatus, partyChatRoom, findMember);
        return getDeliveryPartyDetailRes;
    }

    //배달파티 조회: 검색어로 조회 ,필터 추가
    public GetDeliveryPartiesRes getDeliveryPartiesByKeyword2(int dormitoryId, int cursor, String orderTimeCategory, Integer maxMatching, String keyword, int memberId) {
        // validation: 검색어 빈값
        if(keyword == null || keyword.isBlank()){
            throw new BaseException(BaseResponseStatus.BLANK_KEYWORD);
        }

        OrderTimeCategoryType orderTimeCategoryType = null;

        if( orderTimeCategory != null && !orderTimeCategory.equals("")){
            orderTimeCategoryType = OrderTimeCategoryType.valueOf(orderTimeCategory);
        }

        List<Member> blockList = blockRepository.findBlocksByBlockingMember(memberId).stream()
                .map(block -> block.getBlockedMember())
                .collect(Collectors.toList());

        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD)); // 페이징 요구 객체
        GetDeliveryPartiesRes dto = deliveryPartyQueryRepository.getDeliveryPartiesByKeyword2(dormitoryId, orderTimeCategoryType, maxMatching, keyword, paging, blockList);
        return dto;
    }


    //배달파티 검색 통합 버전
    public GetDeliveryPartiesRes getDeliveryParties(int dormitoryId, int cursor, String orderTimeCategory, Integer maxMatching, int memberId) {

        OrderTimeCategoryType orderTimeCategoryType = null;

        if(StringUtils.hasText(orderTimeCategory)){
            orderTimeCategoryType = OrderTimeCategoryType.valueOf(orderTimeCategory);
        }

        List<Member> blockList = blockRepository.findBlocksByBlockingMember(memberId).stream()
                .map(block -> block.getBlockedMember())
                .collect(Collectors.toList());

        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD)); // 페이징 요구 객체
        GetDeliveryPartiesRes dto = deliveryPartyQueryRepository.findDeliveryPartiesByConditions(dormitoryId, orderTimeCategoryType, maxMatching, paging, blockList);
        return dto;
    }

    //기숙사 별 default 위도, 경도
    public GetDeliveryPartyDefaultLocationRes getDeliveryPartyDefaultLocation(int dormitoryId){
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_DORMITORY));

        Double getLatitude = dormitory.getLocation().getLatitude(); //위도
        Double getLongitude = dormitory.getLocation().getLongitude(); //경도
        GetDeliveryPartyDefaultLocationRes getDeliveryPartyDefaultLocationRes =  new GetDeliveryPartyDefaultLocationRes(getLatitude,getLongitude);

        return getDeliveryPartyDefaultLocationRes;
    }

    //배달파티 삭제
    @Transactional(readOnly = false)
    public PatchDeliveryPartyStatusRes patchDeliveryPartyStatusById(int partyId, JwtInfo jwtInfo) {

        int userId = jwtInfo.getUserId();

        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByPartyId(partyId, userId)
                .orElseThrow(() -> new BaseException(CAN_NOT_DELETE_PARTY));
        deliveryParty.changeStatusToInactive();
        deliveryPartyRepository.save(deliveryParty);

        // 배달파티 멤버 status도 Inactive로 수정
        List<DeliveryPartyMember> deliveryPartyMembers = deliveryPartyMemberRepository.findDeliveryPartyMembersByPartyId(partyId);

        for( DeliveryPartyMember deliveryPartyMember : deliveryPartyMembers ){
            deliveryPartyMember.changeStatusToInactive();
        }

        return PatchDeliveryPartyStatusRes.builder()
                .deliveryPartyId(deliveryParty.getId())
                .status(deliveryParty.getStatus().toString())
                .build();
    }

    @Transactional(readOnly = false)
    public PatchLeaveChiefRes chiefLeaveDeliveryParty(int partyId, String nickName, int userId) {

        Member attemptedChief = memberRepository.findMemberByIdAndStatus(userId).orElseThrow(() -> new BaseException(NOT_EXIST_USER));
        DeliveryParty findParty = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(partyId).orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        if(findParty.isNotChief(attemptedChief)){
            throw new BaseException(INVALID_DELIVERY_PARTY_CHIEF);
        }

        //방장만 있을 때
        if(findParty.memberIsOnlyChief()){
            findParty.deleteParty();
            return new PatchLeaveChiefRes(DELETE_PARTY);
        }

//        //제일 먼저 참여한 방장 후보의 멤버 아이디를 가져온다.
//        int secondDeliverPartyMemberId = findParty.getSecondDeliverPartyMemberId();

        //TODO 프론트 요청에 의한 임시 수정. 나중에 무조건 바꿔아함.
        DeliveryPartyMember candidateForChief = deliveryPartyMemberRepository.findByDeliveryPartyMemberByIdAndNickName(partyId, nickName)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_DELIVERY_PARTY_PARTICIPANT));

//        DeliveryPartyMember candidateForChief = deliveryPartyMemberRepository.findByDeliveryPartyMemberByIdAndStatus(secondDeliverPartyMemberId)
//                .orElseThrow(() -> new BaseException(NOT_EXISTS_DELIVERY_PARTY_PARTICIPANT));

        findParty.leaveNowChiefAndChangeChief(candidateForChief.getParticipant());

        return new PatchLeaveChiefRes(CHANGE_CHIEF);
    }

    // 배달 파티 수동 매칭 마감
    @Transactional(readOnly = false)
    public PatchDeliveryPartyMatchingStatusRes patchDeliveryPartyMatchingStatus(Integer partyId, int userId) {

        //여기서 방장 인증을 진행함.
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndUserIdAndMatchingStatus(partyId, userId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.CAN_NOT_FINISH_DELIVERY_PARTY));
        deliveryParty.changeMatchingStatusToFinish();

        //그러므로 여기서 방장 인증을 진행할 필요가 없음.
        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByDeliveryPartyId(partyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        partyChatRoomRepository.changeIsFinish(new ObjectId(partyChatRoom.getId()));

        deliveryPartyChatService.createChat(userId, partyChatRoom.getId(), "매칭이 마감되었어요", true, null, "publish", "none", false);

        return PatchDeliveryPartyMatchingStatusRes.builder()
                .deliveryPartyId(deliveryParty.getId())
                .matchingStatus(deliveryParty.getMatchingStatus().toString())
                .build();
    }

    public List<GetRecentOngoingPartiesRes> getRecentOngoingDeliveryParties(int userId) {
        List<DeliveryParty> threeRecentDeliveryParty = deliveryPartyQueryRepository.findRecentOngoingDeliveryParty(userId);
        return threeRecentDeliveryParty.stream()
                .map(deliveryParty -> GetRecentOngoingPartiesRes.toDto(deliveryParty))
                .collect(Collectors.toList());
    }

    //진행했던(현재 비활성) 배달 파티 조회
    public GetEndedDeliveryPartiesRes getEndedDeliveryParties(int userId, int cursor){

        //요청 보낸 사용자 Member 찾기
        memberRepository.findMemberByIdAndStatus(userId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));
        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD));
        return deliveryPartyQueryRepository.getEndedDeliveryParties(userId, paging);
    }


}
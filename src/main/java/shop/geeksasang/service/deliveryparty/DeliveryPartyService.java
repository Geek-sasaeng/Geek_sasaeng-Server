package shop.geeksasang.service.deliveryparty;

import lombok.RequiredArgsConstructor;

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
import java.util.Optional;
import java.util.stream.Collectors;

import static shop.geeksasang.config.TransactionManagerConfig.*;
import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;


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

    private static final int PAGING_SIZE = 10;
    private static final String PAGING_STANDARD = "orderTime";
    private static final String DELETE_PARTY = "파티를 삭제했습니다.";
    private static final String CHANGE_CHIEF = "기존 방장을 삭제하고 새로운 방장으로 교체했습니다.";

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PostDeliveryPartyRes registerDeliveryParty(PostDeliveryPartyReq dto, int chiefId, int dormitoryId){

        //파티장 조회
       Member chief = memberRepository.findMemberByIdAndStatus(chiefId)
                .orElseThrow(()-> new BaseException(NOT_EXISTS_PARTICIPANT));

       //신고 3번이상으로 누적된 회원은 파티를 생성할 수 없음
        if(chief.getStatus().equals(BaseStatus.REPORTED)){
            throw new BaseException(CAN_NOT_CREATE_PARTY);
        }

        //카테고리
        FoodCategory foodCategory = foodCategoryRepository.findFoodCategoryById(dto.getFoodCategory())
                .orElseThrow(() ->  new BaseException(NOT_EXISTS_CATEGORY));

        //기숙사
        Dormitory dormitory = dormitoryRepository.findDormitoryById(dormitoryId)
                .orElseThrow(() ->  new BaseException(NOT_EXISTS_DORMITORY));

        //해시태그 -- 기존 로직 유지
        List<HashTag> hashTagList = new ArrayList<>();

        if(dto.isHashTag()){
            HashTag hashTag = hashTagRepository.findById(1).orElseThrow(() -> new BaseException(NOT_EXISTS_HASHTAG));
            hashTagList.add(hashTag);
        }

        //orderTime 분류화
        OrderTimeCategoryType orderTimeCategory = OrderTimeUtils.selectOrderTime(dto.getOrderTime().getHour());

        // 파티 생성 및 저장. 이렇게 의존성이 많이 발생하는데 더 좋은 방법이 있지 않을까?
        DeliveryParty deliveryParty = DeliveryParty.makeParty(dto, orderTimeCategory, dormitory, foodCategory, chief, hashTagList);

        DeliveryPartyMember deliveryPartyMember = new DeliveryPartyMember(chief, deliveryParty);

        //배달파티 저장
        deliveryPartyRepository.save(deliveryParty);

        return PostDeliveryPartyRes.toDto(deliveryParty); //어차피 영속성이 관리하니 id가 들어가므로 생성한걸 보내줘도 된다. 테스트하기 편하므로 이게 더 좋은 코드
    }

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PutDeliveryPartyRes updateDeliveryParty(PutDeliveryPartyReq dto, JwtInfo jwtInfo, int dormitoryId, int partyId){
        int chiefId = jwtInfo.getUserId();

        //요청 보낸 사용자 Member 찾기
        int memberId = jwtInfo.getUserId();
        Member findMember = memberRepository.findMemberByIdAndStatus(memberId).
                orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyById(partyId).
                orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        //요청 보낸 사용자와 파티 chief 비교
        if(!findMember.equals(deliveryParty.getChief())){
            throw new BaseException(NOT_EXISTS_PERMISSION_UPDATE);
        }

        //파티장
        Member chief = memberRepository.findMemberByIdAndStatus(chiefId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        //기숙사
        Dormitory dormitory = dormitoryRepository.findDormitoryById(dormitoryId)
                .orElseThrow(() ->  new BaseException(NOT_EXISTS_DORMITORY));

        //카테고리
        FoodCategory foodCategory = foodCategoryRepository.findFoodCategoryById(dto.getFoodCategory())
                .orElseThrow(() ->  new BaseException(NOT_EXISTS_CATEGORY));

        //해시태그 -- 기존 로직 유지
        List<HashTag> hashTagList = new ArrayList<>();

        if(dto.isHashTag()){
            HashTag hashTag = hashTagRepository.findById(1).orElseThrow(() -> new BaseException(NOT_EXISTS_HASHTAG));
            hashTagList.add(hashTag);
        }
        //orderTime 분류화
        OrderTimeCategoryType orderTimeCategory = OrderTimeUtils.selectOrderTime(dto.getOrderTime().getHour());

        //TODO 마감되면 수정을 못함.

        //파티의 수정 인원이 지금 같거나 커야지 수정이 됨.
        if(deliveryParty.getDeliveryPartyMembers().size() > dto.getMaxMatching()){
            throw new BaseException(CANT_UPDATE_MAX_DELIVERY_PARTY_PARTICIPANT);
        }

        else if(deliveryParty.getDeliveryPartyMembers().size() == dto.getMaxMatching()){
            deliveryParty.changeMatchingStatusToFinish();
        }

        // 파티 수정
        DeliveryParty resDeliveryParty = deliveryParty.updateParty(dto, orderTimeCategory, dormitory, foodCategory, chief, hashTagList);

        return PutDeliveryPartyRes.toDto(resDeliveryParty);

    }

    //배달파티 상세조회:
    @Transactional(readOnly = true, transactionManager = JPA_TRANSACTION_MANAGER)
    public GetDeliveryPartyDetailRes getDeliveryPartyDetailById(int partyId, int memberId){
        //속해있는지
        BelongStatus belongStatus = BelongStatus.N;
        //사용자 본인 여부
        boolean authorStatus = false;

        boolean activeStatus = false;

        //요청 보낸 사용자 Member 찾기
        Member findMember = memberRepository.findById(memberId).
                orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));

        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(partyId).
                orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        //주문 시간이 현재 시간보다 이전이거나 매칭 상태가 Finish 면 오직 방장만이 배달파티 상세보기를 할 수 있다.
        if((deliveryParty.getOrderTime().isBefore(LocalDateTime.now()) | deliveryParty.getMatchingStatus() == MatchingStatus.FINISH) & deliveryParty.getChief() != findMember){
            throw new BaseException(CHIEF_ONLY_SEE_DELIVERY_PARTY);
        }

        //요청 보낸 사용자와 파티 chief 비교
        if(findMember.equals(deliveryParty.getChief())){
            authorStatus = true;
        }

        //요청 보낸 사용자가 이미 파티멤버인지 조회,
        // 로직: 요청 사용자 id, partyId -> deliveryPartyMember 에서 같은 partyId와 memberId 같은 멤버 조회
        Optional<DeliveryPartyMember> optionalDeliveryPartyMember = deliveryPartyMemberRepository
                .findDeliveryPartyMemberByMemberIdAndDeliveryPartyIdNotUseStatus(memberId, partyId);
        if(optionalDeliveryPartyMember.isPresent()){
            belongStatus = BelongStatus.Y;
            DeliveryPartyMember member = optionalDeliveryPartyMember.get();
            activeStatus = member.isActive();
        }


        PartyChatRoom partyChatRoom = partyChatRoomRepository.findByDeliveryPartyId(partyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_CHAT_ROOM));

        return GetDeliveryPartyDetailRes.toDto(deliveryParty, authorStatus, belongStatus, partyChatRoom, findMember, activeStatus);
    }

    //배달파티 조회: 검색어로 조회 ,필터 추가
    @Transactional(readOnly = true, transactionManager = JPA_TRANSACTION_MANAGER)
    public GetDeliveryPartiesRes getDeliveryPartiesByKeyword2(int dormitoryId, int cursor, String orderTimeCategory, Integer maxMatching, String keyword, int memberId) {
        // validation: 검색어 빈값
        if(keyword == null || keyword.isBlank()){
            throw new BaseException(BLANK_KEYWORD);
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
    @Transactional(readOnly = true, transactionManager = JPA_TRANSACTION_MANAGER)
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
    @Transactional(readOnly = true, transactionManager = JPA_TRANSACTION_MANAGER)
    public GetDeliveryPartyDefaultLocationRes getDeliveryPartyDefaultLocation(int dormitoryId){
        Dormitory dormitory = dormitoryRepository.findById(dormitoryId).orElseThrow(() -> new BaseException(NOT_EXISTS_DORMITORY));

        Double getLatitude = dormitory.getLocation().getLatitude(); //위도
        Double getLongitude = dormitory.getLocation().getLongitude(); //경도
        GetDeliveryPartyDefaultLocationRes getDeliveryPartyDefaultLocationRes =  new GetDeliveryPartyDefaultLocationRes(getLatitude,getLongitude);

        return getDeliveryPartyDefaultLocationRes;
    }

    //배달파티 삭제
    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PatchDeliveryPartyStatusRes patchDeliveryPartyStatusById(int partyId, Integer userId) {
        DeliveryParty deliveryParty = deliveryPartyRepository
                .findDeliveryPartyByPartyId(partyId, userId)
                .orElseThrow(() -> new BaseException(CAN_NOT_DELETE_PARTY));

        deliveryParty.deleteParty();

        return PatchDeliveryPartyStatusRes
                .builder()
                .deliveryPartyId(deliveryParty.getId())
                .status(deliveryParty.getStatus().toString())
                .build();
    }

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PatchLeaveChiefRes chiefLeaveDeliveryParty(int partyId, int userId) {

        Member attemptedChief = memberRepository.findMemberByIdAndStatus(userId)
                .orElseThrow(() -> new BaseException(NOT_EXIST_USER));

        DeliveryParty findParty = deliveryPartyRepository.findDeliveryPartyByIdAndStatus(partyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_PARTY));

        if(findParty.isNotChief(attemptedChief)){
            throw new BaseException(INVALID_DELIVERY_PARTY_CHIEF);
        }

        if(findParty.memberIsOnlyChief()){
            findParty.deleteParty();
            return new PatchLeaveChiefRes(DELETE_PARTY);
        }

        findParty.leaveNowChiefAndChangeChief();
        return new PatchLeaveChiefRes(CHANGE_CHIEF);
    }

    @Transactional(readOnly = true, transactionManager = JPA_TRANSACTION_MANAGER)
    public List<GetRecentOngoingPartiesRes> getRecentOngoingDeliveryParties(int userId) {
        List<DeliveryParty> threeRecentDeliveryParty = deliveryPartyQueryRepository.findRecentOngoingDeliveryParty(userId);
        return threeRecentDeliveryParty.stream()
                .map(GetRecentOngoingPartiesRes::toDto)
                .collect(Collectors.toList());
    }

    //진행했던(현재 비활성) 배달 파티 조회
    @Transactional(readOnly = true, transactionManager = JPA_TRANSACTION_MANAGER)
    public GetEndedDeliveryPartiesRes getEndedDeliveryParties(int userId, int cursor){

        //요청 보낸 사용자 Member 찾기
        memberRepository.findMemberByIdAndStatus(userId).
                orElseThrow(() -> new BaseException(NOT_EXISTS_PARTICIPANT));
        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD));
        return deliveryPartyQueryRepository.getEndedDeliveryParties(userId, paging);
    }

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public void changeOrderStatusToDeliveryComplete(int deliveryPartyId) {
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndMatchingStatus(deliveryPartyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_MATCHING_FINISH_PARTY));
        deliveryParty.changeOrderStatusToDeliveryComplete();
    }

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public void changeOrderStatusToOrderComplete(int deliveryPartyId) {
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndMatchingStatus(deliveryPartyId)
                .orElseThrow(() -> new BaseException(NOT_EXISTS_MATCHING_FINISH_PARTY));
        deliveryParty.changeOrderStatusToOrderComplete();
    }

    @Transactional(readOnly = false, transactionManager = JPA_TRANSACTION_MANAGER)
    public PatchDeliveryPartyMatchingStatusRes changeMatchingStatus(Integer partyId, int userId) {
        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdAndUserIdAndMatchingStatus(partyId, userId).
                orElseThrow(() -> new BaseException(CAN_NOT_FINISH_DELIVERY_PARTY));
        deliveryParty.changeMatchingStatusToFinish();

        return PatchDeliveryPartyMatchingStatusRes.builder()
                .deliveryPartyId(deliveryParty.getId())
                .matchingStatus(deliveryParty.getMatchingStatus().toString())
                .build();
    }
}
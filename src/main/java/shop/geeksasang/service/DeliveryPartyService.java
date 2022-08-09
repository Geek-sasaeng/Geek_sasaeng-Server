package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import shop.geeksasang.config.status.BaseStatus;
import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.*;
import shop.geeksasang.dto.deliveryParty.get.*;
import shop.geeksasang.dto.deliveryParty.patch.PatchDeliveryPartyStatusRes;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.*;
import shop.geeksasang.utils.ordertime.OrderTimeUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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

    private static final int PAGING_SIZE = 10;
    private static final String PAGING_STANDARD = "orderTime";
    private static final List<Integer> MATCHING_NUMBER = Arrays.asList(2, 4, 6, 8, 10);


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
    public GetDeliveryPartyDetailRes getDeliveryPartyDetailById(int partyId, JwtInfo jwtInfo){

        //사용자 본인 여부
        boolean authorStatus = false;

        //요청 보낸 사용자 Member 찾기
        int memberId = jwtInfo.getUserId();
        Member findMember = memberRepository.findById(memberId).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        DeliveryParty deliveryParty = deliveryPartyRepository.findDeliveryPartyByIdBeforeOrderTime(partyId, LocalDateTime.now()).
                orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        //요청 보낸 사용자와 파티 chief 비교
        if(findMember.equals(deliveryParty.getChief())){
            authorStatus = true;
        }

        GetDeliveryPartyDetailRes getDeliveryPartyDetailRes = GetDeliveryPartyDetailRes.toDto(deliveryParty,authorStatus);
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

}
package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.config.type.OrderTimeCategoryType;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.domain.*;
import shop.geeksasang.dto.deliveryParty.*;
import shop.geeksasang.dto.email.PostEmailCertificationRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.repository.*;
import shop.geeksasang.utils.ordertime.OrderTimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyService {

    private final DeliveryPartyRepository deliveryPartyRepository;
    private final MemberRepository memberRepository;
    private final DormitoryRepository dormitoryRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final HashTagRepository hashTagRepository;
    private final DeliveryPartyQueryRepository deliveryPartyQueryRepository;

    private static final int PAGING_SIZE = 10;
    private static final String PAGING_STANDARD = "orderTime";
    private static final List<Integer> MATCHING_NUMBER = Arrays.asList(2, 4, 6, 8, 10);


    @Transactional(readOnly = false)
    public PostDeliveryPartyRes registerDeliveryParty(PostDeliveryPartyReq dto, JwtInfo jwtInfo){

        int chiefId = jwtInfo.getUserId();

        //파티장
        Member chief = memberRepository.findById(chiefId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        //기숙사
        Dormitory dormitory = dormitoryRepository.findById(dto.getDormitory())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_DORMITORY));

        //카테고리
        FoodCategory foodCategory = foodCategoryRepository.findById(dto.getFoodCategory())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_CATEGORY));

        //해시태그 -- 기존 로직 유지
        List<HashTag> hashTagList = new ArrayList<>();

        if(dto.isHashTag()){
            HashTag hashTag = hashTagRepository.findById(dto.getFoodCategory()).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_HASHTAG));
            hashTagList.add(hashTag);
        }

        //orderTime 분류화
        OrderTimeCategoryType orderTimeCategory = OrderTimeUtils.selectOrderTime(dto.getOrderTime().getHour());

        // 파티 생성 및 저장. 이렇게 의존성이 많이 발생하는데 더 좋은 방법이 있지 않을까?
        DeliveryParty deliveryParty = DeliveryParty.makeParty(dto, orderTimeCategory, dormitory, foodCategory, chief, hashTagList);

        //배달파티 저장
        DeliveryParty party= deliveryPartyRepository.save(deliveryParty);

        return PostDeliveryPartyRes.toDto(party);
    }

    //배달파티 상세조회:
    public GetDeliveryPartyDetailRes getDeliveryPartyDetailById(int partyId){
        DeliveryParty deliveryParty = deliveryPartyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        GetDeliveryPartyDetailRes getDeliveryPartyDetailRes = GetDeliveryPartyDetailRes.toDto(deliveryParty);
        return getDeliveryPartyDetailRes;
    }

    //배달파티 조회: 검색어로 조회
    public List<GetDeliveryPartiesByKeywordRes> getDeliveryPartiesByKeyword(int dormitoryId, String keyword, int cursor){
        // validation: 검색어 빈값
        if(keyword == null || keyword.isBlank()){
            throw new BaseException(BaseResponseStatus.BLANK_KEYWORD);
        }
        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD)); // 페이징 요구 객체

        Slice<DeliveryParty> deliveryParties = deliveryPartyRepository.findDeliveryPartiesByKeyword(dormitoryId, keyword, paging); // 페이징 반환 객체

        return deliveryParties.stream()
                .map(deliveryParty -> GetDeliveryPartiesByKeywordRes.toDto(deliveryParty)) // 배열 원소 변경 한번에 적용
                .collect(Collectors.toList()); // List로 변경
    }


    //배달파티 검색 통합 버전
    public List<GetDeliveryPartiesRes> getDeliveryParties(int dormitoryId, int cursor, String orderTimeCategory, Integer maxMatching) {

        OrderTimeCategoryType orderTimeCategoryType = null;

        if( orderTimeCategory != null && !orderTimeCategory.equals("")){
            orderTimeCategoryType = OrderTimeCategoryType.valueOf(orderTimeCategory);
        }

        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD)); // 페이징 요구 객체
        List<DeliveryParty> deliveryParties = deliveryPartyQueryRepository.findDeliveryPartiesByConditions(dormitoryId, orderTimeCategoryType, maxMatching, paging);

        return deliveryParties.stream()
                .map(deliveryParty -> GetDeliveryPartiesRes.toDto(deliveryParty))
                .collect(Collectors.toList());
    }

    //
    public GetDeliveryPartyDefaultLocationRes getDeliveryPartyDefaultLocation(int domitoryId){
        Dormitory dormitory = dormitoryRepository.findById(domitoryId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_DORMITORY));

        Double getLatitude = dormitory.getLocation().getLatitude(); //위도
        Double getLongtitude = dormitory.getLocation().getLongitude(); //경도
        GetDeliveryPartyDefaultLocationRes getDeliveryPartyDefaultLocationRes =  new GetDeliveryPartyDefaultLocationRes(getLatitude,getLongtitude);

        return getDeliveryPartyDefaultLocationRes;
    }

}
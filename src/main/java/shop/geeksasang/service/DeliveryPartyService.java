package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.config.domain.OrderTimeCategoryType;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.domain.*;
import shop.geeksasang.dto.deliveryParty.*;
import shop.geeksasang.repository.*;
import shop.geeksasang.utils.ordertime.OrderTimeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static shop.geeksasang.config.exception.BaseResponseStatus.NOT_SPECIFIED_VALUE;


@Transactional
@Service
@RequiredArgsConstructor
public class DeliveryPartyService {

    private final DeliveryPartyRepository deliveryPartyRepository;
    private final MemberRepository memberRepository;
    private final DormitoryRepository dormitoryRepository;
    private final FoodCategoryRepository foodCategoryRepository;
    private final HashTagRepository hashTagRepository;

    private static final int PAGING_SIZE = 10;
    private static final String PAGING_STANDARD = "orderTime";
    private static final List<Integer> MATCHING_NUMBER = Arrays.asList(2, 4, 6, 8, 10);


    @Transactional(readOnly = false)
    public PostDeliveryPartyRes registerDeliveryParty(PostDeliveryPartyReq dto){

        //파티장
        Member chief = memberRepository.findById(dto.getChief())
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTICIPANT));

        //기숙사
        Dormitory dormitory = dormitoryRepository.findById(dto.getDormitory())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_DORMITORY));

        //카테고리
        FoodCategory foodCategory = foodCategoryRepository.findById(dto.getFoodCategory())
                .orElseThrow(() ->  new BaseException(BaseResponseStatus.NOT_EXISTS_CATEGORY));

        //해시태그
        List<HashTag> hashTagList = new ArrayList<>();
        for (Integer hashTagId : dto.getHashTag()) {
            HashTag hashTag = hashTagRepository.findById(hashTagId).orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_HASHTAG));
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

    //배달파티 조회: 기숙사 별 전체목록
    public List<GetDeliveryPartiesRes> getDeliveryPartiesByDormitoryId(int dormitoryId, int cursor){

        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD ));

        Slice<DeliveryParty> deliveryParties = deliveryPartyRepository.findDeliveryPartiesByDormitoryId(dormitoryId, paging);

        return deliveryParties.stream()
                .map(deliveryParty -> GetDeliveryPartiesRes.toDto(deliveryParty))
                .collect(Collectors.toList());
    }

    //배달파티 상세조회:
    public GetDeliveryPartyDetailRes getDeliveryPartyDetailById(int partyId){
        DeliveryParty deliveryParty = deliveryPartyRepository.findById(partyId)
                .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_PARTY));

        GetDeliveryPartyDetailRes getDeliveryPartyDetailRes = GetDeliveryPartyDetailRes.toDto(deliveryParty);
        return getDeliveryPartyDetailRes;
    }


    // 배달파티 조회: 인원수
    public List<GetDeliveryPartyByMaxMatchingRes> getDeliveryPartyByMaxMatching(int dormitoryId, int maxMatching, int cursor) {

        // 인원수 입렵값 validation
        if(!MATCHING_NUMBER.contains(maxMatching)){
            throw new BaseException(NOT_SPECIFIED_VALUE);
        }

        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD));

        Slice<DeliveryParty> deliveryParties = deliveryPartyRepository.findDeliveryPartiesByMaxMatching(dormitoryId, maxMatching, paging);

        return deliveryParties.stream()
                .map(deliveryParty -> GetDeliveryPartyByMaxMatchingRes.toDto(deliveryParty))
                .collect(Collectors.toList());
    }

    // 배달파티 조회: orderTimeCategory 시간대
    public List<GetDeliveryPartyByOrderTimeRes> getDeliveryPartyByOrderTime(int dormitoryId, int cursor, String orderTimeCategory) {

        OrderTimeCategoryType orderTimeCategoryType = OrderTimeCategoryType.valueOf(orderTimeCategory);

        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD));

        Slice<DeliveryParty> deliveryParties = deliveryPartyRepository.findDeliveryPartiesByOrderTime(dormitoryId, orderTimeCategoryType, paging);

        return deliveryParties.stream()
                .map(deliveryParty -> GetDeliveryPartyByOrderTimeRes.toDto(deliveryParty))
                .collect(Collectors.toList());
    }

}
package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.domain.*;
import shop.geeksasang.dto.deliveryParty.GetDeliveryPartiesRes;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;
import shop.geeksasang.repository.*;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class DeliveryPartyService {

    private final DeliveryPartyRepository deliveryPartyRepository;
    private final MemberRepository memberRepository;
    private final DomitoryRepository domitoryRepository;
    private final HashTagRepository hashTagRepository;
    private final CategoryRepository categoryRepository;
    private final DeliveryPartyHashTagRepository deliveryPartyHashTagRepository;

    private static final int PAGING_SIZE = 10;
    private static final String PAGING_STANDARD = "orderTime";


    @Transactional(readOnly = false) // ?
    public DeliveryParty registerDeliveryParty(PostDeliveryPartyReq dto){

        // 파티 생성 및 저장
        DeliveryParty deliveryParty = dto.toEntity();

        //파티장
        Member chief = memberRepository.findById(dto.getChief())
                .orElseThrow(() -> new RuntimeException(""));
        deliveryParty.connectChief(chief); // 배달 파티에 파티장 연결

        //도미토리
        Domitory domitory = domitoryRepository.findById(dto.getDomitory())
                .orElseThrow(() -> new RuntimeException(""));
        deliveryParty.connectDomitory(domitory);

        //카테고리
        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new RuntimeException(""));
        deliveryParty.connectCategory(category);

        //해시태그
        DeliveryPartyHashTag deliveryPartyHashTag = DeliveryPartyHashTag.builder()
                .deliveryParty(deliveryParty)
                .build();

        for(Integer hashTagId : dto.getHashTag()){
            HashTag hashTag = hashTagRepository.findById(hashTagId)
                    .orElseThrow(() -> new BaseException(BaseResponseStatus.NOT_EXISTS_HASHTAG));

            deliveryPartyHashTag.connectPartyHashTag(deliveryParty,hashTag);
        }
        //여기 의문***
        deliveryPartyRepository.save(deliveryParty);
        deliveryPartyHashTagRepository.save(deliveryPartyHashTag);

        // 반환
        return deliveryParty;
    }

    //배달파티 조회: 전체목록
    public List<GetDeliveryPartiesRes> getDeliveryPartiesByDomitoryId(int domitoryId, int cursor){

        PageRequest paging = PageRequest.of(cursor, PAGING_SIZE, Sort.by(Sort.Direction.ASC, PAGING_STANDARD ));

        Slice<DeliveryParty> deliveryParties = deliveryPartyRepository.findDeliveryPartiesByDomitoryId(domitoryId, paging);

        return deliveryParties.stream()
                .map(deliveryParty -> GetDeliveryPartiesRes.toDto(deliveryParty))
                .collect(Collectors.toList());
    }

    //배달파티 상세조회:
    public DeliveryParty getDeliveryParty(int partyId){
        DeliveryParty deliveryParty= deliveryPartyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException(""));
        return deliveryParty;
    }

}

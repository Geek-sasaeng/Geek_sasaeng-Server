package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.domain.*;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;
import shop.geeksasang.repository.*;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

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

            System.out.println("여기 들어옴ㅋㅋㅋㅋ");
            deliveryPartyHashTag.connectPartyHashTag(deliveryParty,hashTag);
        }

        //여기 의문***
        deliveryPartyRepository.save(deliveryParty);

        deliveryPartyHashTagRepository.save(deliveryPartyHashTag);

        // 반환
        return deliveryParty;
    }


    //배달파티 조회: 전체목록
    @Transactional(readOnly = false) // ?
    public List<DeliveryParty> getAllDeliveryParty(){
        List<DeliveryParty> getDliveryPartyRes = deliveryPartyRepository.findAll();
        return getDliveryPartyRes;
    }

    //배달파티 조회: 전체목록


    //배달파티 조회: 전체목록
    @Transactional(readOnly = false) // ?
    public List<DeliveryParty> getDeliveryPartyById(int domitoryId){
        List<DeliveryParty> getDliveryPartyRes = deliveryPartyRepository.findDeliveryPartiesByDomitoryId(domitoryId);
        return getDliveryPartyRes;
    }


    //배달파티 상세조회:
    @Transactional(readOnly = false)
    public DeliveryParty getDeliveryParty(int partyId){
        DeliveryParty deliveryParty= deliveryPartyRepository.findById(partyId)
                .orElseThrow(() -> new RuntimeException(""));
        return deliveryParty;
    }

}

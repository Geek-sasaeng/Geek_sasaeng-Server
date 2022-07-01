package shop.geeksasang.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shop.geeksasang.domain.*;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;
import shop.geeksasang.repository.*;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class DeliveryPartyService {

    private final DeliveryPartyRepository deliveryPartyRepository;
    private final MemberRepository memberRepository;
    private final DomitoryRepository domitoryRepository;
    private final HashTagRepository hashTagRepository;
    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = false) // ?
    public DeliveryParty registerDeliveryParty(PostDeliveryPartyReq dto){
        System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"+dto.getOrderTime());
        System.out.println("ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ"+dto.getOrderTime().getHour());


        LocalDateTime localDateTime = LocalDateTime.of(2021, 1, 1, 0, 0, 0);


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

        //해시태그
//        HashTag hashTag = hashTagRepository.findById(dto.getHashTag())
//                .orElseThrow(() -> new RuntimeException(""));
//        deliveryParty.connectHashTag(hashTag);
        deliveryParty.connectHashTag();


        //카테고리
        Category category = categoryRepository.findById(dto.getCategory())
                .orElseThrow(() -> new RuntimeException(""));
        deliveryParty.connectCategory(category);


        deliveryPartyRepository.save(deliveryParty);
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
    @Transactional(readOnly = false) // ?
    public List<DeliveryParty> getDeliveryPartyById(int domitoryId){
        List<DeliveryParty> getDliveryPartyRes = deliveryPartyRepository.findDeliveryPartiesByDomitoryId(domitoryId);
        return getDliveryPartyRes;
    }
}

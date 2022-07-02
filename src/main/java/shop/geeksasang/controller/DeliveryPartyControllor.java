package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.dto.deliveryParty.GetDeliveryPartyRes;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyRes;
import shop.geeksasang.service.DeliveryPartyService;

import java.util.List;

@RestController
@RequestMapping("/deliveryParties")
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class DeliveryPartyControllor {

    private final DeliveryPartyService deliveryPartyService;

    //배달 파티 생성
    @PostMapping
    public BaseResponse<PostDeliveryPartyRes> registerDeliveryParty(@RequestBody PostDeliveryPartyReq dto){
        DeliveryParty deliveryParty = deliveryPartyService.registerDeliveryParty(dto);

        PostDeliveryPartyRes postDeliveryPartyRes = PostDeliveryPartyRes.toDto(deliveryParty);

        return new BaseResponse<>(postDeliveryPartyRes);
    }

    //배달파티 조회: 전체목록
    @GetMapping
    public BaseResponse<List<DeliveryParty>> getAllDeliveryParty(){
        List<DeliveryParty> getDeliveryPartyRes = deliveryPartyService.getAllDeliveryParty();

        return new BaseResponse<>(getDeliveryPartyRes);
    }


    //배달파티 조회:
    @GetMapping("/get")
    public BaseResponse<List<DeliveryParty>> getDeliveryPartyById(@RequestParam int domitoryId){
        List<DeliveryParty> getDeliveryPartyRes = deliveryPartyService.getDeliveryPartyById(domitoryId);
        return new BaseResponse<>(getDeliveryPartyRes);
    }

    //배달파티 상세조회:
    @GetMapping("/get/detail")
    public BaseResponse<DeliveryParty> getDeliveryPartyDetailById(@RequestParam int partyId){
        DeliveryParty deliveryParty = deliveryPartyService.getDeliveryParty(partyId);
        return new BaseResponse<>(deliveryParty);
    }
}

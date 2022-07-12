package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.dto.deliveryParty.GetDeliveryPartiesRes;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyRes;
import shop.geeksasang.service.DeliveryPartyService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.util.List;

@RestController
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
    @NoIntercept
    @GetMapping("/{domitoryId}/delivery-parties")
    public BaseResponse<List<GetDeliveryPartiesRes>> getAllDeliveryParty(@PathVariable int domitoryId, @RequestParam int cursor){
        List<GetDeliveryPartiesRes> response = deliveryPartyService.getDeliveryPartiesByDomitoryId(domitoryId, cursor);
        return new BaseResponse<>(response);
    }


    //배달파티 상세조회:
    @GetMapping("/get/detail")
    public BaseResponse<DeliveryParty> getDeliveryPartyDetailById(@RequestParam int partyId){
        DeliveryParty deliveryParty = deliveryPartyService.getDeliveryParty(partyId);
        return new BaseResponse<>(deliveryParty);
    }

}

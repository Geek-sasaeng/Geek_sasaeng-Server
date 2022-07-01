package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
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

}

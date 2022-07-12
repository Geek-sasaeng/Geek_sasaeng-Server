package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberRes;
import shop.geeksasang.service.DeliveryPartyMemberService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DeliveryPartyMemberController {
    private final DeliveryPartyMemberService deliveryPartyMemberService;

    @PostMapping("/deliveryPartyMember")
    public BaseResponse<PostDeliveryPartyMemberRes> joinDeliveryPartyRoom(@RequestBody PostDeliveryPartyMemberReq dto){

        DeliveryPartyMember deliveryPartyMember= deliveryPartyMemberService.joinDeliveryPartyMember(dto);
        PostDeliveryPartyMemberRes postDeliveryPartyMemberRes = PostDeliveryPartyMemberRes.toDto(deliveryPartyMember);
        return new BaseResponse<>(postDeliveryPartyMemberRes);
    }
}

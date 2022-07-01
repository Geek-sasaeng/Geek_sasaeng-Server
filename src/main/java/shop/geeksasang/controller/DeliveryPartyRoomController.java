package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryPartyRoom;
import shop.geeksasang.dto.deliveryPartyRoom.PostDeliveryPartyRoomReq;
import shop.geeksasang.dto.deliveryPartyRoom.PostDeliveryPartyRoomRes;
import shop.geeksasang.service.DeliveryPartyRoomService;

@RestController
@RequestMapping("/deliveryPartyRoom")
@RequiredArgsConstructor
public class DeliveryPartyRoomController {
    DeliveryPartyRoomService deliveryPartyRoomService;

    @PostMapping
    public BaseResponse<PostDeliveryPartyRoomRes> joinDeliveryPartyRoom(@RequestBody PostDeliveryPartyRoomReq dto){

        DeliveryPartyRoom deliveryPartyRoom= deliveryPartyRoomService.joinDeliveryPartyRoom(dto);

        PostDeliveryPartyRoomRes postDeliveryPartyRoomRes = PostDeliveryPartyRoomRes.toDto(deliveryPartyRoom);
        return new BaseResponse<>(postDeliveryPartyRoomRes);
    }
}

package shop.geeksasang.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.deliveryPartyMenu.PostDeliveryPartyMenuReq;
import shop.geeksasang.dto.deliveryPartyMenu.PostDeliveryPartyMenuRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.DeliveryPartyMenuService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class DeliveryPartyMenuController {

    private final DeliveryPartyMenuService deliveryPartyMenuService;

    private final static String SUCCESS_MESSAGE = "메뉴 생성에 성공했습니다.";

    @PostMapping("/delivery-party-menu")
    public BaseResponse<String> registerDeliveryPartyMenu(@RequestBody PostDeliveryPartyMenuReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyMenuService.registerDeliveryPartyMenu(dto, jwtInfo.getUserId());
        return new BaseResponse<>(SUCCESS_MESSAGE);
    }
}

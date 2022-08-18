package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.firebase.PostDeliveryComplicated;
import shop.geeksasang.dto.firebase.PostFcmReq;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.FirebaseCloudMessageService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class FcmContoroller {

    private final FirebaseCloudMessageService firebaseCloudMessageService;

    @NoIntercept
    @PostMapping("/api/fcm")
    public ResponseEntity pushMessage(@RequestBody PostFcmReq requestDTO) throws IOException {
        System.out.println(requestDTO.getTargetToken() + " "
                +requestDTO.getTitle() + " " + requestDTO.getBody());

        firebaseCloudMessageService.sendMessageTo(
                requestDTO.getTargetToken(),
                requestDTO.getTitle(),
                requestDTO.getBody());
        return ResponseEntity.ok().build();
    }

    // 배달 완료 메시지 전송 API
    @PostMapping("delivery-party/complicated")
    public BaseResponse<String> deliveryComplicated(@RequestBody PostDeliveryComplicated requestDTO, HttpServletRequest request) throws IOException {

        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        firebaseCloudMessageService.sendDeliveryComplicatedMessage(requestDTO.getUuid(), jwtInfo.getUserId());

        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

}

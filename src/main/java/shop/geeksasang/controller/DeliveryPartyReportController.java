package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.report.PostDeliveryPartyReportRegisterReq;
import shop.geeksasang.service.DeliveryPartyReportService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/reports/delivery-parties")
@RestController
@RequiredArgsConstructor
public class DeliveryPartyReportController {

    private final DeliveryPartyReportService deliveryPartyReportService;
    //TODO 차단 서비스 구현 처리할 것.

    public static final String SUCCESS_MESSAGE = "신고 생성에 성공하셨습니다";

    @PostMapping
    public BaseResponse<String> registerDeliveryPartyReport(@Validated @RequestBody PostDeliveryPartyReportRegisterReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyReportService.registerDeliveryPartyReport(dto, jwtInfo);
        return new BaseResponse<>(SUCCESS_MESSAGE);
    }
}

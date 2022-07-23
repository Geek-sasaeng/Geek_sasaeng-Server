package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.report.PostDeliveryPartyReportRegisterReq;
import shop.geeksasang.service.DeliveryPartyReportService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/reports/delivery-parties")
@RestController
@RequiredArgsConstructor
public class DeliveryPartyReportController {

    private final DeliveryPartyReportService deliveryPartyReportService;

    @PostMapping
    public Object registerDeliveryPartyReport(@RequestBody PostDeliveryPartyReportRegisterReq dto,  HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyReportService.registerDeliveryPartyReport(dto, jwtInfo);
        return null;
    }
}

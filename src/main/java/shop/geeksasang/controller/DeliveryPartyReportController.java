package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

    @ApiOperation(value = "생성: 배달 파티 신고 생성", notes = "배달 파티 신고를 생성한다. 성공하면 성공 메세지만 리턴합니다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2017 ,message ="하루 신고 최대 횟수를 초과하셨습니다."),
            @ApiResponse(code =2010 ,message ="존재하지 않는 파티입니다."),
            @ApiResponse(code =2018 ,message ="중복 신고는 불가능합니다."),
            @ApiResponse(code =2019 ,message ="존재하지 않는 신고 카테고리입니다"),
            @ApiResponse(code =2204 ,message ="존재하지 않는 회원 id 입니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PostMapping
    public BaseResponse<String> registerDeliveryPartyReport(@Validated @RequestBody PostDeliveryPartyReportRegisterReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyReportService.registerDeliveryPartyReport(dto, jwtInfo);
        return new BaseResponse<>(SUCCESS_MESSAGE);
    }
}

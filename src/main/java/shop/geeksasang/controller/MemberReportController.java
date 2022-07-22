package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.report.PostMemberReportRegisterReq;
import shop.geeksasang.service.MemberReportService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/reports/members")
@RestController
@RequiredArgsConstructor
public class MemberReportController {

    private final MemberReportService memberReportService;

    public static final String SUCCESS_MESSAGE = "신고 생성에 성공하셨습니다";

    @PostMapping()
    public BaseResponse<String> registerMemberReport(@Validated @RequestBody PostMemberReportRegisterReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        memberReportService.registerMemberReport(dto, jwtInfo);
        return new BaseResponse<>(SUCCESS_MESSAGE);
    }
}

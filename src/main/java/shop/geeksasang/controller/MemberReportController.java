package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.report.PostMemberReportRegisterReq;
import shop.geeksasang.service.MemberReportService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/reports/members")
@RestController
@RequiredArgsConstructor
public class MemberReportController {

    private final MemberReportService memberReportService;

    @PostMapping("/member")
    public Object registerMemberReport(@RequestBody PostMemberReportRegisterReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        memberReportService.registerMemberReport(dto, jwtInfo);
        return null;
    }
}

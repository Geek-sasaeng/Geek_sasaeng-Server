package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.CreateMemberReq;
import shop.geeksasang.dto.CreateMemberRes;
import shop.geeksasang.service.MailSendService;
import shop.geeksasang.service.MemberService;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    private final MailSendService mss;

    @PostMapping
    public BaseResponse<CreateMemberRes> createMember(@RequestBody CreateMemberReq dto){
        Member member = memberService.createMember(dto);

        CreateMemberRes createMemberRes = CreateMemberRes.toDto(member);
        return new BaseResponse<>(createMemberRes);
    }
    // 이메일 인증 보내기

}

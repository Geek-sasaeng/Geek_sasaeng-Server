package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.EmailReq;
import shop.geeksasang.dto.member.CreateMemberReq;
import shop.geeksasang.dto.member.CreateMemberRes;
import shop.geeksasang.dto.member.PatchMemberPhoneNumberReq;
import shop.geeksasang.dto.member.PatchMemberPhoneNumberRes;
import shop.geeksasang.service.MemberService;
import shop.geeksasang.service.SendEmailService;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SendEmailService sendEmailService;

    @PostMapping
    public BaseResponse<CreateMemberRes> createMember(@Validated @RequestBody CreateMemberReq dto){
        Member member = memberService.createMember(dto);

        CreateMemberRes createMemberRes = CreateMemberRes.toDto(member);
        return new BaseResponse<>(createMemberRes);
    }

    // 이메일 인증 번호 보내기
    @PostMapping("/email")
    public BaseResponse<String> authEmail(@RequestBody @Valid EmailReq req){
        sendEmailService.authEmail(req);
        String response = "성공적으로 인증 메일을 보냈습니다.";
        return new BaseResponse<String>(response);
    }


    // 수정: 폰 번호
    @PatchMapping("phone-number/{id}")
    public BaseResponse<PatchMemberPhoneNumberRes> updateMemberPhoneNumber(@PathVariable("id") int id, @RequestBody PatchMemberPhoneNumberReq dto){
        Member member = memberService.updateMemberPhoneNumber(id,dto);

        //응답 형식으로 변환
        PatchMemberPhoneNumberRes patchMemberPhoneNumberRes = PatchMemberPhoneNumberRes.toDto(member);
        //반환
        return new BaseResponse<>(patchMemberPhoneNumberRes);
    }


    // 수정: 폰 인증 번호

}

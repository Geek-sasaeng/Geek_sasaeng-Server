package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.EmailReq;
import shop.geeksasang.dto.member.*;
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
    @PatchMapping("/phone-number/{id}")
    public BaseResponse<PatchPhoneNumberRes> updatePhoneNumber(@PathVariable("id") int id, @Validated @RequestBody PatchPhoneNumberReq dto){
        Member member = memberService.updatePhoneNumber(id,dto);

        //응답 형식으로 변환
        PatchPhoneNumberRes patchPhoneNumberRes = PatchPhoneNumberRes.toDto(member);
        //반환
        return new BaseResponse<>(patchPhoneNumberRes);
    }


    // 수정: 폰 인증 번호
    @PatchMapping("/phone-vaid-key/{id}")
    public BaseResponse<PatchPhoneValidKeyRes> updatePhoneValidKey(@PathVariable("id") int id, @Validated @RequestBody PatchPhoneValidKeyReq dto){
        // 서비스에 폰 인증번호 수정 요청
        Member member = memberService.updatePhoneValidKey(id,dto);
        //응답 형식으로 변환
        PatchPhoneValidKeyRes patchPhoneValidKeyRes = PatchPhoneValidKeyRes.toDto(member);
        //반환
        return new BaseResponse<>(patchPhoneValidKeyRes);
    }


    // 수정: 프로필 이미지
    @PatchMapping("/profile-img-url/{id}")
    public BaseResponse<PatchProfileImgUrlRes> updateProfileImgUrl(@PathVariable("id") int id,@Validated @RequestBody PatchProfileImgUrlReq dto){
        Member member = memberService.updateProfileImgUrl(id,dto);

        //응답 형식으로 변환
        PatchProfileImgUrlRes patchProfileImgUrlRes = PatchProfileImgUrlRes.toDto(member);
        return new BaseResponse<>(patchProfileImgUrlRes);
    }


    // 수정: 회원정보 동의 수정
    @PatchMapping("/information-agree-status/{id}")
    public BaseResponse<PatchInformationAgreeStatusRes> updateInformationAgreeStatus(@PathVariable("id") int id,@Validated @RequestBody PatchInformationAgreeStatusReq dto){
        Member member = memberService.updateInformationAgreeStatus(id,dto);

        //응답 형식으로 변환
        PatchInformationAgreeStatusRes patchInformationAgreeStatusRes = PatchInformationAgreeStatusRes.toDto(member);
        return new BaseResponse<>(patchInformationAgreeStatusRes);
    }

}

package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Member;
import shop.geeksasang.domain.University;
import shop.geeksasang.dto.EmailReq;
import shop.geeksasang.dto.member.*;
import shop.geeksasang.service.MemberService;
import shop.geeksasang.service.SendEmailService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SendEmailService sendEmailService;

    // 회원가입
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

    // 닉네임 수정하기
    @PatchMapping("/nickName/{id}")
    public BaseResponse<PatchNicknameRes> updateNickname(@Validated @PathVariable("id") int id, @RequestBody @Valid PatchNicknameReq dto) {
        Member member = memberService.UpdateNickname(id, dto);

        PatchNicknameRes patchNicknameRes = PatchNicknameRes.toDto(member);
       return new BaseResponse<>(patchNicknameRes);
    }

    // 회원 탈퇴하기 - status "INACTIVE"로 수정
    @PatchMapping("/account_delete/{id}")
    public BaseResponse<String> updateMemberStatus(@PathVariable("id") int id, @RequestBody @Valid PatchMemberStatusReq dto) {
        memberService.UpdateMemberStatus(id, dto);
        String response = "회원 탈퇴가 성공하였습니다.";
        return new BaseResponse<String>(response);
    }

    // 비밀번호 수정하기
    @PatchMapping("/modify_password/{id}")
    public BaseResponse<PatchPasswordRes> updatePassword(@PathVariable("id") int id, @RequestBody @Valid PatchPasswordReq dto) {
        Member member = memberService.UpdatePassword(id, dto);

        PatchPasswordRes patchPasswordRes = PatchPasswordRes.toDto(member);
        return new BaseResponse<>(patchPasswordRes);
    }

    // 아이디 중복 확인하기
    @GetMapping("/id_duplicated")
    public BaseResponse<String> checkIdDuplicated(@RequestBody @Valid CheckIdReq dto) {
        String response = memberService.checkId(dto);

        return new BaseResponse<>(response);
    }

}

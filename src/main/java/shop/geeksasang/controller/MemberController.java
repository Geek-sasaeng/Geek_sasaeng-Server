package shop.geeksasang.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.email.EmailCertificationReq;
import shop.geeksasang.dto.email.EmailReq;
import shop.geeksasang.domain.University;
import shop.geeksasang.dto.EmailReq;
import shop.geeksasang.dto.member.*;
import shop.geeksasang.service.MemberService;
import shop.geeksasang.service.EmailService;
import shop.geeksasang.utils.clientip.ClientIpUtils;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    private final EmailService emailService;

    // 회원가입
    @PostMapping
    @NoIntercept
    public BaseResponse<CreateMemberRes> createMember(@Validated @RequestBody CreateMemberReq dto){
        Member member = memberService.createMember(dto);
        CreateMemberRes createMemberRes = CreateMemberRes.toDto(member);
        return new BaseResponse<>(createMemberRes);
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
        memberService.checkId(dto);

        return new BaseResponse<>(BaseResponseStatus.VALID_ID);
    }

}

    // 이메일 인증 번호 보내기
    @NoIntercept
    @PostMapping("/email")
    public BaseResponse<String> authEmail(@RequestBody @Valid EmailReq req, HttpServletRequest servletRequest) {
        String clientIp = ClientIpUtils.getClientIp(servletRequest);
        emailService.sendEmail(req, clientIp);
        return new BaseResponse<>(BaseResponseStatus.SEND_MAIL_SUCCESS);
    }

    // 이메일 인증 번호 확인하기
    @NoIntercept
    @PostMapping("/email/check")
    public BaseResponse<String> checkEmail(@RequestBody @Valid EmailCertificationReq req) {
        boolean check = emailService.checkEmailCertification(req);
        if(check){
            return new BaseResponse<>(BaseResponseStatus.VALID_EMAIL_NUMBER);
        }else{
            return new BaseResponse<>(BaseResponseStatus.INVALID_EMAIL_NUMBER);
        }
    }
}
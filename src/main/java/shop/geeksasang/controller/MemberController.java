package shop.geeksasang.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.EmailReq;
import shop.geeksasang.dto.member.*;
import shop.geeksasang.service.MemberService;
import shop.geeksasang.service.SendEmailService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;
    private final SendEmailService sendEmailService;

    // 회원가입
    @PostMapping
    @NoIntercept
    public BaseResponse<CreateMemberRes> createMember(@Validated @RequestBody CreateMemberReq dto){
        Member member = memberService.createMember(dto);
        CreateMemberRes createMemberRes = CreateMemberRes.toDto(member);
        return new BaseResponse<>(createMemberRes);
    }

    // 이메일 인증 번호 보내기
    @PostMapping("/email")
    @NoIntercept
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


    // 중복 확인: 닉네임
    @GetMapping("/nickname-duplicated")
    public BaseResponse<String> checkNickNameDuplicated(@Validated @RequestBody GetCheckNickNameDuplicatedReq dto){

        memberService.checkNickNameDuplicated(dto);

        return new BaseResponse<>(BaseResponseStatus.VALID_NICKNAME);
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
}

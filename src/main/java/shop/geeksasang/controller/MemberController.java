package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.Getter;
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
import shop.geeksasang.dto.email.EmailReq;
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
    @ApiOperation(value = "사용자 회원가입", notes = "사용자의 정보들을 이용해서 회원가입을 진행한다.")
    @PostMapping
    @NoIntercept
    public BaseResponse<CreateMemberRes> createMember(@Validated @RequestBody CreateMemberReq dto){
        Member member = memberService.createMember(dto);
        CreateMemberRes createMemberRes = CreateMemberRes.toDto(member);
        return new BaseResponse<>(createMemberRes);
    }


    // 수정: 폰 번호
    @ApiOperation(value = "수정: 폰 번호", notes = "사용자의 폰번호를 입력받아 폰번호를 수정한다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2205 ,message ="존재하지 않는 회원 id 입니다."),
    })
    @PatchMapping("/phone-number/{id}")
    public BaseResponse<PatchPhoneNumberRes> updatePhoneNumber(@PathVariable("id") int id, @Validated @RequestBody PatchPhoneNumberReq dto){
        Member member = memberService.updatePhoneNumber(id,dto);

        //응답 형식으로 변환
        PatchPhoneNumberRes patchPhoneNumberRes = PatchPhoneNumberRes.toDto(member);
        //반환
        return new BaseResponse<>(patchPhoneNumberRes);
    }


    // 수정: 폰 인증 번호
    @ApiOperation(value = "수정: 폰 인증 번호", notes = "사용자의 폰 인증번호 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2205 ,message ="존재하지 않는 회원 id 입니다."),
    })
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
    @ApiOperation(value = "수정: 프로필 이미지", notes = "사용자의 프로필 이미지 url을 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2205 ,message ="존재하지 않는 회원 id 입니다."),
    })
    @PatchMapping("/profile-img-url/{id}")
    public BaseResponse<PatchProfileImgUrlRes> updateProfileImgUrl(@PathVariable("id") int id,@Validated @RequestBody PatchProfileImgUrlReq dto){
        Member member = memberService.updateProfileImgUrl(id,dto);

        //응답 형식으로 변환
        PatchProfileImgUrlRes patchProfileImgUrlRes = PatchProfileImgUrlRes.toDto(member);
        return new BaseResponse<>(patchProfileImgUrlRes);
    }


    // 수정: 회원정보 동의 수정
    @ApiOperation(value = "수정: 회원정보 동의 수정", notes = "사용자의 동의여부 Y 를 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2205 ,message ="존재하지 않는 회원 id 입니다."),
    })
    @PatchMapping("/information-agree-status/{id}")
    public BaseResponse<PatchInformationAgreeStatusRes> updateInformationAgreeStatus(@PathVariable("id") int id,@Validated @RequestBody PatchInformationAgreeStatusReq dto){
        Member member = memberService.updateInformationAgreeStatus(id,dto);

        //응답 형식으로 변환
        PatchInformationAgreeStatusRes patchInformationAgreeStatusRes = PatchInformationAgreeStatusRes.toDto(member);
        return new BaseResponse<>(patchInformationAgreeStatusRes);
    }

    //확인: 새로 입력한 폰 인증번호 맞는지 확인
    @ApiOperation(value = "확인: 새로 입력한 폰 인증번호 맞는지 확인", notes = "사용자의 폰번호, 폰 인증번호를 이용해서 인증번호 일치 확인.")
    @ApiResponses({
            @ApiResponse(code =1201 ,message ="폰 인증번호가 일치합니다."),
            @ApiResponse(code =2205 ,message ="존재하지 않는 회원 id 입니다."),
            @ApiResponse(code =2203 ,message ="이미 등록된 전호번호입니다."),
            @ApiResponse(code =2204 ,message ="폰 인증번호가 다릅니다."),
    })
    @GetMapping("/phone-vaid-key/{id}")
    @NoIntercept
    public BaseResponse<String> checkPhoneValidKey(@PathVariable("id") int id,@Validated @RequestBody GetCheckPhoneValidKeyReq dto){
        memberService.checkPhoneValidKey(id,dto);

        return new BaseResponse<>(BaseResponseStatus.VALID_PHONEVALIDKEY);
    }

    // 중복 확인: 닉네임
    @ApiOperation(value = "중복 확인: 닉네임", notes = "사용자의 닉네임을 이용해서 중복확인을 한다.")
    @ApiResponses({
            @ApiResponse(code =1202 ,message ="사용 가능한 닉네임 입니다."),
            @ApiResponse(code =2600 ,message ="중복되는 유저 닉네임입니다"),
    })
    @GetMapping("/nickname-duplicated")
    @NoIntercept // jwt 검사 제외
    public BaseResponse<String> checkNickNameDuplicated(@Validated @RequestBody GetCheckNickNameDuplicatedReq dto){

        memberService.checkNickNameDuplicated(dto);

        return new BaseResponse<>(BaseResponseStatus.VALID_NICKNAME);
    }


    // 수정: 닉네임 수정하기
    @ApiOperation(value = "수정: 닉네임 수정하기", notes = "수정할 닉네임을 입력받아 수정한다.")
    @PatchMapping("/nickName/{id}")
    public BaseResponse<PatchNicknameRes> updateNickname(@Validated @PathVariable("id") int id, @RequestBody @Valid PatchNicknameReq dto) {
        Member member = memberService.UpdateNickname(id, dto);

        PatchNicknameRes patchNicknameRes = PatchNicknameRes.toDto(member);
        return new BaseResponse<>(patchNicknameRes);
    }

    // 삭제: 회원 탈퇴하기 - status "INACTIVE"로 수정
    @ApiOperation(value = "삭제: 회원 탈퇴하기", notes = "회원 id를 이용해 status \"INACTIVE\"로 수정.")
    @PatchMapping("/account_delete/{id}")
    public BaseResponse<String> updateMemberStatus(@PathVariable("id") int id, @RequestBody @Valid PatchMemberStatusReq dto) {
        memberService.UpdateMemberStatus(id, dto);
        String response = "회원 탈퇴가 성공하였습니다.";
        return new BaseResponse<String>(response);
    }

    // 수정: 비밀번호 수정하기
    @ApiOperation(value = "수정: 비밀번호 수정하기", notes = "수정할 비밀번호를 입력받아 수정.")
    @PatchMapping("/modify_password/{id}")
    public BaseResponse<PatchPasswordRes> updatePassword(@PathVariable("id") int id, @RequestBody @Valid PatchPasswordReq dto) {
        Member member = memberService.UpdatePassword(id, dto);

        PatchPasswordRes patchPasswordRes = PatchPasswordRes.toDto(member);
        return new BaseResponse<>(patchPasswordRes);
    }

    // 아이디 중복 확인하기
    @NoIntercept
    @GetMapping("/id_duplicated")
    public BaseResponse<String> checkIdDuplicated(@RequestBody @Valid CheckIdReq dto) {
        memberService.checkId(dto);

        return new BaseResponse<>(BaseResponseStatus.VALID_ID);
    }



    // 이메일 인증 번호 보내기
    @ApiOperation(value = "이메일 인증번호 보내기", notes = "사용자의 이메일을 입력받아 인증번호를 보낸다.")
    @NoIntercept
    @PostMapping("/email")
    public BaseResponse<String> authEmail(@RequestBody @Valid EmailReq req, HttpServletRequest servletRequest) {
        String clientIp = ClientIpUtils.getClientIp(servletRequest);
        emailService.sendEmail(req, clientIp);
        return new BaseResponse<>(BaseResponseStatus.SEND_MAIL_SUCCESS);
    }


    // 이메일 인증 번호 확인하기
    @ApiOperation(value = "이메일 인증번호 확인하기", notes = "사용자의 이메일과, 수신한 이메일 인증번호를 이용해서 일치하는지 확인한다.")
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
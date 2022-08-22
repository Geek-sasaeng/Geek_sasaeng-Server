package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.Member;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.member.get.GetCheckIdReq;
import shop.geeksasang.dto.member.get.GetMemberRes;
import shop.geeksasang.dto.member.get.GetNickNameDuplicatedReq;
import shop.geeksasang.dto.member.patch.*;
import shop.geeksasang.dto.member.post.PostRegisterReq;
import shop.geeksasang.dto.member.post.PostRegisterRes;
import shop.geeksasang.dto.member.post.PostSocialRegisterReq;
import shop.geeksasang.dto.member.post.PostSocialRegisterRes;
import shop.geeksasang.service.MemberService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // 회원가입
    @ApiOperation(value = "사용자 회원가입", notes = "사용자의 정보들을 이용해서 회원가입을 진행한다.")
    @ApiResponses({
            @ApiResponse(code = 1000 , message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2205 , message = "존재하지 않는 회원 id 입니다."),
            @ApiResponse(code = 2006 , message = "중복되는 유저 아이디입니다"),
            @ApiResponse(code = 2007 , message = "중복되는 유저 이메일입니다"),
            @ApiResponse(code = 2201 , message = "회원 정보동의 status가 Y가 아닙니다."),
            @ApiResponse(code = 2008 , message = "존재하지 않는 학교 이름입니다"),
            @ApiResponse(code = 4000 , message =  "서버 오류입니다.")
    })
    @PostMapping
    @NoIntercept
    public BaseResponse<PostRegisterRes> registerMember(@Validated @RequestBody PostRegisterReq dto){
        PostRegisterRes postCreateMemberRes = memberService.registerMember(dto);
        return new BaseResponse<>(postCreateMemberRes);
    }

    // 소셜 회원가입
    @ApiOperation(value = "사용자 소셜 회원가입", notes = "사용자의 정보들을 이용해서 소셜 회원가입을 진행한다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message = "요청에 성공하였습니다."),
            @ApiResponse(code =2007 ,message = "중복되는 유저 이메일입니다"),
            @ApiResponse(code =2201 ,message = "회원 정보동의 status가 Y가 아닙니다."),
            @ApiResponse(code =2008 ,message = "존재하지 않는 학교 이름입니다"),
            @ApiResponse(code =4000, message = "서버 오류입니다.")
    })
    @NoIntercept
    @PostMapping("/social")
    public BaseResponse<PostSocialRegisterRes> registerSocialMember(@Validated @RequestBody PostSocialRegisterReq dto){
        PostSocialRegisterRes postSocialRegisterRes = memberService.registerSocialMember(dto);
        return new BaseResponse<>(postSocialRegisterRes);
    }

    // 수정: 회원정보 동의 수정
    @ApiOperation(value = "수정: 회원정보 동의 수정", notes = "사용자의 동의여부 Y 를 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code = 1000 , message = "요청에 성공하셨습니다."),
            @ApiResponse(code = 2205 , message = "존재하지 않는 회원 id 입니다."),
            @ApiResponse(code = 4000 , message = "서버 오류입니다.")
    })
    @PatchMapping("/information-agree-status/{id}")
    public BaseResponse<PatchInformationAgreeStatusRes> updateInformationAgreeStatus(@PathVariable("id") int id, @Validated @RequestBody PatchInformationAgreeStatusReq dto){
        Member member = memberService.updateInformationAgreeStatus(id,dto);

        //응답 형식으로 변환
        PatchInformationAgreeStatusRes patchInformationAgreeStatusRes = PatchInformationAgreeStatusRes.toDto(member);
        return new BaseResponse<>(patchInformationAgreeStatusRes);
    }

    // 중복 확인: 닉네임
    @ApiOperation(value = "중복 확인: 닉네임", notes = "사용자의 닉네임을 이용해서 중복확인을 한다.")
    @ApiResponses({
            @ApiResponse(code = 1202 , message = "사용 가능한 닉네임입니다."),
            @ApiResponse(code = 2600 , message = "중복된 닉네임입니다."),
            @ApiResponse(code = 4000 , message = "서버 오류입니다.")
    })
    @PostMapping("/nickname-duplicated")
    @NoIntercept // jwt 검사 제외
    public BaseResponse<String> checkNickNameDuplicated(@Validated @RequestBody GetNickNameDuplicatedReq dto){

        memberService.checkNickNameDuplicated(dto);

        return new BaseResponse<>(BaseResponseStatus.VALID_NICKNAME);
    }

    // 삭제: 회원 탈퇴하기 - status "INACTIVE"로 수정
    @ApiOperation(value = "삭제: 회원 탈퇴하기", notes = "회원 id를 이용해 status \"INACTIVE\"로 수정.")
    @ApiResponses({
            @ApiResponse(code = 2009 ,message = "존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2601 ,message = "이미 탈퇴한 회원입니다"),
            @ApiResponse(code = 2005 ,message = "입력하신 두 비밀번호가 다릅니다."),
            @ApiResponse(code = 2011 ,message = "비밀번호가 틀립니다."),
            @ApiResponse(code = 4000, message = "서버 오류입니다.")
    })
    @PatchMapping("/account-delete/{id}")
    public BaseResponse<String> updateMemberStatus(@PathVariable("id") int id, @RequestBody @Valid PatchMemberStatusReq dto) {
        memberService.updateMemberStatus(id, dto);
        String response = "회원 탈퇴가 성공하였습니다.";
        return new BaseResponse<>(response);
    }

    // 수정: 멤버 정보 수정하기
    @ApiOperation(value = "수정: 멤버 정보 수정하기", notes = "맴버 정보 프로필, 기숙사, 닉네임, 비밀번호를 수정.")
    @ApiResponses({
            @ApiResponse(code = 1000 , message = "요청에 성공하셨습니다."),
            @ApiResponse(code = 2005 , message = "입력하신 두 비밀번호가 다릅니다."),
            @ApiResponse(code = 2204 , message = "존재하지 않는 회원 id 입니다."),
            @ApiResponse(code = 2606 , message = "기숙사가 존재하지 않습니다."),
            @ApiResponse(code = 4000 , message = "서버 오류입니다.")
    })
    @PatchMapping()
    public BaseResponse<PatchMemberRes> updateMember(@RequestBody @Validated PatchMemberReq dto, HttpServletRequest request) {
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        PatchMemberRes res  = memberService.updateMember(dto, jwtInfo.getUserId());

        return new BaseResponse<>(res);
    }

    // 수정: FCM토큰 수정
    @ApiOperation(value = "수정: FCM토큰 수정하기", notes = "수정할 FCM토큰 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code = 2009 , message = "존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 , message = "서버 오류입니다.")
    })
    @PatchMapping("/fcm_token")
    public BaseResponse<PatchFcmTokenRes> updateFcmToken(@RequestBody @Validated PatchFcmTokenReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        PatchFcmTokenRes res = memberService.updateFcmToken(dto, jwtInfo.getUserId());
        return new BaseResponse<>(res);
    }

    // 아이디 중복 확인하기
    @ApiOperation(value = "확인: 아이디 중복 확인하기", notes = "아이디 입력받아 중복 여부 체크.")
    @ApiResponses({
            @ApiResponse(code = 2603 ,message = "중복된 아이디입니다"),
            @ApiResponse(code = 1601 ,message = "사용 가능한 아이디입니다"),
            @ApiResponse(code = 4000, message = "서버 오류입니다.")
    })
    @NoIntercept
    @PostMapping("/id-duplicated")
    public BaseResponse<String> checkIdDuplicated(@RequestBody @Valid GetCheckIdReq dto) {
        memberService.checkId(dto);
        return new BaseResponse<>(BaseResponseStatus.VALID_ID);
    }

    @ApiOperation(value = "조회: 사용자의 정보 조회", notes = "사용자의 인덱스 id 입력받아 정보 조회")
    @ApiResponses({
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @GetMapping()
    public BaseResponse<GetMemberRes> getMember(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        GetMemberRes res = memberService.getMember(jwtInfo.getUserId());
        return new BaseResponse<>(res);
    }
}
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
import shop.geeksasang.dto.member.get.GetCheckIdReq;
import shop.geeksasang.dto.member.get.GetNickNameDuplicatedReq;
import shop.geeksasang.dto.member.patch.*;
import shop.geeksasang.dto.member.post.PostRegisterReq;
import shop.geeksasang.dto.member.post.PostRegisterRes;
import shop.geeksasang.dto.member.post.PostSocialRegisterReq;
import shop.geeksasang.dto.member.post.PostSocialRegisterRes;
import shop.geeksasang.service.MemberService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.validation.Valid;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;
    // 회원가입
    @ApiOperation(value = "사용자 회원가입", notes = "사용자의 정보들을 이용해서 회원가입을 진행한다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code =2205 ,message ="존재하지 않는 회원 id 입니다."),
            @ApiResponse(code =2006 ,message ="중복되는 유저 아이디입니다"),
            @ApiResponse(code =2007 ,message ="중복되는 유저 이메일입니다"),
            @ApiResponse(code =2201 ,message ="회원 정보동의 status가 Y가 아닙니다."),
            @ApiResponse(code =2008 ,message ="존재하지 않는 학교 이름입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
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
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code =2007 ,message ="중복되는 유저 이메일입니다"),
            @ApiResponse(code =2201 ,message ="회원 정보동의 status가 Y가 아닙니다."),
            @ApiResponse(code =2008 ,message ="존재하지 않는 학교 이름입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @NoIntercept
    @PostMapping("/social")
    public BaseResponse<PostSocialRegisterRes> registerSocialMember(@Validated @RequestBody PostSocialRegisterReq dto){
        PostSocialRegisterRes postSocialRegisterRes = memberService.registerSocialMember(dto);
        return new BaseResponse<>(postSocialRegisterRes);
    }

    // 수정: 프로필 이미지
    @ApiOperation(value = "수정: 프로필 이미지", notes = "사용자의 프로필 이미지 url을 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2205 ,message ="존재하지 않는 회원 id 입니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
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
            @ApiResponse(code=4000,message = "서버 오류입니다.")
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
            @ApiResponse(code =1202 ,message ="사용 가능한 닉네임입니다."),
            @ApiResponse(code =2600 ,message ="중복된 닉네임입니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PostMapping("/nickname-duplicated")
    @NoIntercept // jwt 검사 제외
    public BaseResponse<String> checkNickNameDuplicated(@Validated @RequestBody GetNickNameDuplicatedReq dto){

        memberService.checkNickNameDuplicated(dto);

        return new BaseResponse<>(BaseResponseStatus.VALID_NICKNAME);
    }


    // 수정: 닉네임 수정하기
    @ApiOperation(value = "수정: 닉네임 수정하기", notes = "수정할 닉네임을 입력받아 수정한다.")
    @ApiResponses({
            @ApiResponse(code =2600 ,message ="중복된 닉네임입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PatchMapping("/nickName/{id}")
    public BaseResponse<PatchNicknameRes> updateNickname(@Validated @PathVariable("id") int id, @RequestBody @Valid PatchNicknameReq dto) {
        Member member = memberService.updateNickname(id, dto);

        PatchNicknameRes patchNicknameRes = PatchNicknameRes.toDto(member);
        return new BaseResponse<>(patchNicknameRes);
    }

    // 삭제: 회원 탈퇴하기 - status "INACTIVE"로 수정
    @ApiOperation(value = "삭제: 회원 탈퇴하기", notes = "회원 id를 이용해 status \"INACTIVE\"로 수정.")
    @ApiResponses({
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code =2601 ,message ="이미 탈퇴한 회원입니다"),
            @ApiResponse(code =2005 ,message ="입력하신 두 비밀번호가 다릅니다."),
            @ApiResponse(code =2011 ,message ="비밀번호가 틀립니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PatchMapping("/account-delete/{id}")
    public BaseResponse<String> updateMemberStatus(@PathVariable("id") int id, @RequestBody @Valid PatchMemberStatusReq dto) {
        memberService.updateMemberStatus(id, dto);
        String response = "회원 탈퇴가 성공하였습니다.";
        return new BaseResponse<String>(response);
    }

    // 수정: 비밀번호 수정하기
    @ApiOperation(value = "수정: 비밀번호 수정하기", notes = "수정할 비밀번호를 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code =2601 ,message ="이미 탈퇴한 회원입니다"),
            @ApiResponse(code =2005 ,message ="입력하신 두 비밀번호가 다릅니다."),
            @ApiResponse(code =2011 ,message ="비밀번호가 틀립니다."),
            @ApiResponse(code =2602 ,message ="기존 비밀번호와 동일합니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PatchMapping("/modify-password/{id}")
    public BaseResponse<PatchPasswordRes> updatePassword(@PathVariable("id") int id, @RequestBody @Valid PatchPasswordReq dto) {
        Member member = memberService.updatePassword(id, dto);

        PatchPasswordRes patchPasswordRes = PatchPasswordRes.toDto(member);
        return new BaseResponse<>(patchPasswordRes);
    }

    // 수정: 기숙사 수정하기
    @ApiOperation(value = "수정: 기숙사 수정하기", notes = "수정할 기숙사를 입력받아 수정.")
    @ApiResponses({
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code =2601 ,message ="이미 탈퇴한 회원입니다"),
            @ApiResponse(code =2606 ,message ="기숙사가 존재하지 않습니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PatchMapping("/dormitory/{id}")
    public BaseResponse<PatchDormitoryRes> updateDormitory(@PathVariable("id") int id, @RequestBody @Validated PatchDormitoryReq dto) {
        Member member = memberService.updateDormitory(id, dto);

        PatchDormitoryRes patchDormitoryRes = PatchDormitoryRes.toDto(member);
        return new BaseResponse<>(patchDormitoryRes);
    }

    // 아이디 중복 확인하기
    @ApiOperation(value = "확인: 아이디 중복 확인하기", notes = "아이디 입력받아 중복 여부 체크.")
    @ApiResponses({
            @ApiResponse(code =2603 ,message ="중복된 아이디입니다"),
            @ApiResponse(code =1601 ,message ="사용 가능한 아이디입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @NoIntercept
    @PostMapping("/id-duplicated")
    public BaseResponse<String> checkIdDuplicated(@RequestBody @Valid GetCheckIdReq dto) {
        memberService.checkId(dto);
        return new BaseResponse<>(BaseResponseStatus.VALID_ID);
    }
}
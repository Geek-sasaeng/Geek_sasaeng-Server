package shop.geeksasang.controller.deliveryparty;


import com.sun.net.httpserver.Authenticator;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.SuccessCommonRes;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchForceOutMembersReq;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchLeaveMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberRes;

import shop.geeksasang.dto.deliveryPartyMember.patch.PatchAccountTransferStatusReq;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchAccountTransferStatusRes;

import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.deliveryparty.DeliveryPartyMemberService;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor
public class DeliveryPartyMemberController {
    private final DeliveryPartyMemberService deliveryPartyMemberService;

    @ApiOperation(value = "생성: 배달 파티 멤버 추가", notes = "배달 파티 Id와 멤버 Id JWT를 받아 배달 파티에 해당 멤버를 추가할 수 있다.")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2020 ,message ="이미 파티에 참여하고 있습니다."),
            @ApiResponse(code = 2611 ,message ="참여할 수 없는 파티입니다."),
            @ApiResponse(code = 2615 ,message ="파티 신청 시간이 끝났습니다."),
            @ApiResponse(code = 2614 ,message ="매칭이 완료된 파티입니다."),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PostMapping("/delivery-party-member")
    public BaseResponse<PostDeliveryPartyMemberRes> joinDeliveryPartyMember(@RequestBody PostDeliveryPartyMemberReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PostDeliveryPartyMemberRes postDeliveryPartyMemberRes = deliveryPartyMemberService.joinDeliveryPartyMember(dto.getPartyId(), jwtInfo.getUserId());
        return new BaseResponse<>(postDeliveryPartyMemberRes);
    }

    //배달파티(채팅방)에서 나가기(방장 아닌 경우)
    @ApiOperation(value = "배달파티(채팅방) 에서 나가기", notes = "사용자(방장x)는 배달파티(채팅방)에서 나갈 수 있습니다. \n" +
            "사용자가 참여중인 배달파티에 한 해 나갈 수 있으며 Req 바디값에 채팅방의 uuid를 작성해야 합니다.(jwt 토큰 값 필요) ")
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message = "존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2010, message = "존재하지 않는 파티입니다"),
            @ApiResponse(code = 2405, message = "참여중인 배달파티 정보를 찾을 수 없습니다."),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    }
    )
    @PatchMapping("/delivery-party/member")
    public BaseResponse<String> patchDeliveryPartyMemberStatus(@RequestBody @Validated  PatchLeaveMemberReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        String response = deliveryPartyMemberService.patchDeliveryPartyMemberStatus(dto.getPartyId(), jwtInfo.getUserId());

        return new BaseResponse<>(response);
    }

    // 수정: 파티멤버 송금 완료상태 수정
    @ApiOperation(value = "수정: 송금 완료상태 수정 값", notes = "파티멤버의 송금 완료상태를 입력받아 수정할 수 있다.")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PatchMapping("/delivery-party-member/account-transfer-status")
    public BaseResponse<PatchAccountTransferStatusRes> updateAccountTransferStatus(@RequestBody @Validated PatchAccountTransferStatusReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PatchAccountTransferStatusRes res = deliveryPartyMemberService.updateAccountTransferStatus(dto,jwtInfo.getUserId());
        return new BaseResponse<>(res);
    }



    @ApiOperation(value = "수정: 배달 파티 멤버 강제 퇴장", notes = "방장은 배달파티 멤버들을 강제 퇴장시킬 수 있다.")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2020 ,message ="이미 파티에 참여하고 있습니다."),
            @ApiResponse(code = 2611 ,message ="참여할 수 없는 파티입니다."),
            @ApiResponse(code = 2615 ,message ="파티 신청 시간이 끝났습니다."),
            @ApiResponse(code = 2614 ,message ="매칭이 완료된 파티입니다."),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PatchMapping("/delivery-party-members")
    public BaseResponse<SuccessCommonRes> patchMembersByChief(@RequestBody @Validated PatchForceOutMembersReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        deliveryPartyMemberService.forceOutMembersByChief(dto.getPartyId(), dto.getMembersId(), jwtInfo.getUserId());
        return new BaseResponse<>(new SuccessCommonRes());
    }
}

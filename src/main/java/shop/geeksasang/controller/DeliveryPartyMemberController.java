package shop.geeksasang.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberRes;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchAccountTransferStatusReq;
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchAccountTransferStatusRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.DeliveryPartyMemberService;


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
    @PostMapping("/deliveryPartyMember")
    public BaseResponse<PostDeliveryPartyMemberRes> joinDeliveryPartyMember(@RequestBody PostDeliveryPartyMemberReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PostDeliveryPartyMemberRes postDeliveryPartyMemberRes = deliveryPartyMemberService.joinDeliveryPartyMember(dto, jwtInfo.getUserId());
        return new BaseResponse<>(postDeliveryPartyMemberRes);
    }

    // 수정: 파티멤버 송금 완료상태 수정
    @ApiOperation(value = "수정: 송금 완료상태 수정 값", notes = "파티멤버의 송금 완료상태를 입력받아 수정할 수 있다.")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PatchMapping("/deliveryPartyMember/account-transfer-status")
    public BaseResponse<PatchAccountTransferStatusRes> updateAccountTransferStatus(@RequestBody @Validated PatchAccountTransferStatusReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PatchAccountTransferStatusRes res = deliveryPartyMemberService.updateAccountTransferStatus(dto,jwtInfo.getUserId());
        return new BaseResponse<>(res);
    }
}

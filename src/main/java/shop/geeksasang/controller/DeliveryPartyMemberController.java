package shop.geeksasang.controller;


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
import shop.geeksasang.dto.deliveryPartyMember.patch.PatchLeaveMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.post.PostDeliveryPartyMemberRes;
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
            @ApiResponse(code = 2010 ,message ="존재하지 않는 파티입니다"),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PostMapping("/deliveryPartyMember")
    public BaseResponse<PostDeliveryPartyMemberRes> joinDeliveryPartyMember(@RequestBody PostDeliveryPartyMemberReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PostDeliveryPartyMemberRes postDeliveryPartyMemberRes = deliveryPartyMemberService.joinDeliveryPartyMember(dto, jwtInfo.getUserId());
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
    @PatchMapping("/delivery-party/leave")
    public BaseResponse<String> patchDeliveryPartyMemberStatus(@Validated @RequestBody PatchLeaveMemberReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        String response = deliveryPartyMemberService.patchDeliveryPartyMemberStatus(dto,jwtInfo);

        return new BaseResponse<>(response);
    }
}

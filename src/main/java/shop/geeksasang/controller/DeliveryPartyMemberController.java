package shop.geeksasang.controller;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryPartyMember;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberReq;
import shop.geeksasang.dto.deliveryPartyMember.PostDeliveryPartyMemberRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.DeliveryPartyMemberService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class DeliveryPartyMemberController {
    private final DeliveryPartyMemberService deliveryPartyMemberService;

    @ApiOperation(value = "생성: 배달 파티 멤버 추가", notes = "배달 파티 Id와 멤버 Id JWT를 받아 배달 파티에 해당 멤버를 추가할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code =2010 ,message ="존재하지 않는 파티입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PostMapping("/deliveryPartyMember")
    public BaseResponse<PostDeliveryPartyMemberRes> joinDeliveryPartyMember(@RequestBody PostDeliveryPartyMemberReq dto, HttpServletRequest request){

        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        DeliveryPartyMember deliveryPartyMember= deliveryPartyMemberService.joinDeliveryPartyMember(dto, jwtInfo);
        PostDeliveryPartyMemberRes postDeliveryPartyMemberRes = PostDeliveryPartyMemberRes.toDto(deliveryPartyMember);
        return new BaseResponse<>(postDeliveryPartyMemberRes);
    }
}

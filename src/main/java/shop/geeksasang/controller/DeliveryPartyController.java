package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.deliveryParty.get.*;
import shop.geeksasang.dto.deliveryParty.patch.PatchDeliveryPartyStatusRes;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.post.PostDeliveryPartyRes;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.put.PutDeliveryPartyRes;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.DeliveryPartyService;

import javax.servlet.http.HttpServletRequest;

import java.util.List;


@RestController
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class DeliveryPartyController {

    private final DeliveryPartyService deliveryPartyService;

    //배달 파티 생성
    @ApiOperation(value = "배달 파티 생성", notes = "사용자는 배달 파티를 생성할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다"),
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code =2606 ,message ="기숙사가 존재하지 않습니다"),
            @ApiResponse(code =2402 ,message ="존재하지 않는 카테고리입니다"),
            @ApiResponse(code =4000 ,message = "서버 오류입니다.")
    })
    @PostMapping("/{dormitoryId}/delivery-party")
    public BaseResponse<PostDeliveryPartyRes> registerDeliveryParty(@PathVariable("dormitoryId") int dormitoryId, @Validated @RequestBody PostDeliveryPartyReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        PostDeliveryPartyRes postDeliveryPartyRes = deliveryPartyService.registerDeliveryParty(dto, jwtInfo, dormitoryId);
        return new BaseResponse<>(postDeliveryPartyRes);
    }

    //배달 파티 수정
    @ApiOperation(value = "배달 파티 수정", notes = "글을 작성한 사용자는 배달 파티 내용을 수정할 수 있습니다. \n"+
            "* 게시물 수정은 기존(수정 전) 게시물 데이터를 body에 담아 요청해야 합니다.(jwt토큰 값 필요) ")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다"),
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code =2606 ,message ="기숙사가 존재하지 않습니다"),
            @ApiResponse(code =2402 ,message ="존재하지 않는 카테고리입니다"),
            @ApiResponse(code = 2010, message = "존재하지 않는 파티입니다."),
            @ApiResponse(code = 2403, message = "수정권한이 없는 유저입니다."),
            @ApiResponse(code =4000 ,message = "서버 오류입니다.")
    })
    @PutMapping("/{dormitoryId}/delivery-party/{partyId}")
    public BaseResponse<PutDeliveryPartyRes> updateDeliveryParty(@PathVariable("dormitoryId") int dormitoryId,@PathVariable("partyId") int partyId, @Validated @RequestBody PutDeliveryPartyReq dto, HttpServletRequest request){

        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        PutDeliveryPartyRes putDeliveryPartyRes = deliveryPartyService.updateDeliveryParty(dto, jwtInfo, dormitoryId, partyId);
        return new BaseResponse<>(putDeliveryPartyRes);
    }

    //배달파티 조회 (필터 및 전체 조회)
    @ApiOperation(value = "배달파티 조회", notes = "cursor은 0부터 시작. dormitoryId는 현재 대학교 id. 쿼리 스트링(orderTimeCategory, maxMatching)은 생략 가능합니다 \n " +
            "예시 1. 필터 기반 검색이 아닌 배달 파티 전체 조회 : https://geeksasaeng.shop/1/delivery-parties?cursor=0 \n" +
            "예시 2. 필터를 기반으로 배달 파티 검색 https://geeksasaeng.shop/1/delivery-parties?cursor=0&orderTimeCategory=DINNER&maxMatching=3  ")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code=4000, message = "서버 오류입니다.")
    })
    @GetMapping("/{dormitoryId}/delivery-parties")
    public BaseResponse<GetDeliveryPartiesRes> GetDeliveryParties(@PathVariable int dormitoryId,
                                                                    @RequestParam int cursor, @RequestParam(required = false) String orderTimeCategory, @RequestParam(required = false) Integer maxMatching){
        GetDeliveryPartiesRes res = deliveryPartyService.getDeliveryParties(dormitoryId, cursor, orderTimeCategory, maxMatching);
        return new BaseResponse<>(res);
    }

    //배달파티 조회: 상세조회
    @ApiOperation(value = "조회: 배달파티 상세조회", notes = "배달파티 게시물을 선택하면 상세 정보들을 볼 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 파티입니다."),
            @ApiResponse(code =2009 ,message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @GetMapping("/delivery-party/{partyId}")
    public BaseResponse<GetDeliveryPartyDetailRes> getDeliveryPartyDetailById(@PathVariable("partyId") int partyId, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        GetDeliveryPartyDetailRes response = deliveryPartyService.getDeliveryPartyDetailById(partyId, jwtInfo);

        return new BaseResponse<>(response);
    }

    //배달파티 조회: 검색어(키워드)로 조회
    @ApiOperation(value = "조회 : 검색어를 포함하는 배달파티 목록 조회", notes = "해당 기숙사의 배달파티 목록울 검색어로 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code=2205,message = "검색어를 입력해주세요"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @GetMapping("/{dormitoryId}/delivery-parties/keyword")
    public BaseResponse<GetDeliveryPartiesRes> getDeliveryPartiesByKeyword(@PathVariable("dormitoryId") int dormitoryId, @RequestParam String keyword, @RequestParam int cursor){
        GetDeliveryPartiesRes response = deliveryPartyService.getDeliveryPartiesByKeyword(dormitoryId, keyword, cursor);
        return new BaseResponse<>(response);
    }

    //기숙사별 default 위도, 경도 조회
    @GetMapping("/{dormitoryId}/default-location")
    public  BaseResponse<GetDeliveryPartyDefaultLocationRes> getDeliveryPartyDefaultLocation(@PathVariable("dormitoryId") int dormitoryId){
        GetDeliveryPartyDefaultLocationRes response = deliveryPartyService.getDeliveryPartyDefaultLocation(dormitoryId);
        return new BaseResponse<>(response);
    }

    // 배달파티 삭제
    @ApiOperation(value = "삭제 : 배달파티 삭제", notes = "삭제할 배달파티의 아이디값을 받아 배달파티를 삭제할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code =2010 ,message ="존재하지 않는 파티입니다"),
            @ApiResponse(code=2609,message = "이미 삭제된 배달파티 입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PatchMapping("/delivery-party/{partyId}")
    public BaseResponse<PatchDeliveryPartyStatusRes> patchDeliveryPartyStatusById(@PathVariable("partyId") int partyId, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        PatchDeliveryPartyStatusRes response = deliveryPartyService.patchDeliveryPartyStatusById(partyId, jwtInfo);
        return new BaseResponse<>(response);
    }
}
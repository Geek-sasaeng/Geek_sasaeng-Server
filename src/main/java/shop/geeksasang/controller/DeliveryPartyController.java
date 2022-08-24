package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.deliveryParty.get.*;
import shop.geeksasang.dto.deliveryParty.patch.PatchDeliveryPartyMatchingStatusRes;
import shop.geeksasang.dto.deliveryParty.patch.PatchDeliveryPartyStatusRes;
import shop.geeksasang.dto.deliveryParty.patch.PatchLeaveChiefReq;
import shop.geeksasang.dto.deliveryParty.patch.PatchLeaveChiefRes;
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

        PostDeliveryPartyRes postDeliveryPartyRes = deliveryPartyService.registerDeliveryParty(dto, jwtInfo.getUserId(), dormitoryId);
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
    public BaseResponse<GetDeliveryPartiesRes> GetDeliveryParties(@PathVariable int dormitoryId, HttpServletRequest request,
                                                                    @RequestParam int cursor, @RequestParam(required = false) String orderTimeCategory, @RequestParam(required = false) Integer maxMatching){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        GetDeliveryPartiesRes res = deliveryPartyService.getDeliveryParties(dormitoryId, cursor, orderTimeCategory, maxMatching, jwtInfo.getUserId() );
        return new BaseResponse<>(res);
    }

    //배달파티 조회: 상세조회
    @ApiOperation(value = "조회: 배달파티 상세조회", notes = "배달파티 게시물을 선택하면 상세 정보들을 볼 수 있다.")
    @ApiResponses({
            @ApiResponse(code = 1000 , message = "요청에 성공하셨습니다."),
            @ApiResponse(code = 2010,  message = "존재하지 않는 파티입니다."),
            @ApiResponse(code = 2009 , message = "존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2023 , message = "배달 파티 주문 시간이 지나거나 매팅 상태가 마감인 경우는 방장만 배달 파티 상세보기가 가능합니다."),
            @ApiResponse(code = 4000,  message = "서버 오류입니다.")
    })
    @GetMapping("/delivery-party/{partyId}")
    public BaseResponse<GetDeliveryPartyDetailRes> getDeliveryPartyDetailById(@PathVariable("partyId") int partyId, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        GetDeliveryPartyDetailRes response = deliveryPartyService.getDeliveryPartyDetailById(partyId, jwtInfo.getUserId());

        return new BaseResponse<>(response);
    }

    //배달파티 조회: 검색어(키워드)로 조회 필터링 조건 추가
    @ApiOperation(value = "배달파티 검색어(키워드) 조회", notes = "cursor은 0부터 시작. dormitoryId는 현재 대학교 id. 쿼리 스트링(orderTimeCategory, maxMatching)은 생략 가능합니다 \n " +
            "예시 1. 필터 기반 검색이 아닌 배달 파티 검색어 조회 : https://geeksasaeng.shop/1/delivery-parties/keyword?cursor=0&keyword=chicken \n" +
            "예시 2. 검색어+필터 배달 파티 검색 https://geeksasaeng.shop/1/delivery-parties/keyword?cursor=0&orderTimeCategory=DINNER&maxMatching=3&keyword=chicken  ")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code=2205,message = "검색어를 입력해주세요"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @GetMapping("/{dormitoryId}/delivery-parties/keyword")
    public BaseResponse<GetDeliveryPartiesRes> getDeliveryPartiesByKeyword2(@PathVariable int dormitoryId, HttpServletRequest request,
                                                                  @RequestParam int cursor, @RequestParam(required = false) String orderTimeCategory, @RequestParam(required = false) Integer maxMatching, @RequestParam String keyword){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        GetDeliveryPartiesRes res = deliveryPartyService.getDeliveryPartiesByKeyword2(dormitoryId, cursor, orderTimeCategory, maxMatching, keyword, jwtInfo.getUserId());
        return new BaseResponse<>(res);
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

    @ApiOperation(value = "배달파티 방장 삭제 및 교체 ",
            notes = "배달 파티 인원이 방장 1명이라면 파티 삭제 및 방장도 삭제. 배달파티 인원이 2인 이상이면 방장을 배달 파티 멤버에서 제외하고, 방장을 다른 파티 멤버로 교체한다.")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2010 ,message ="존재하지 않는 파티입니다."),
            @ApiResponse(code = 2021 ,message ="파티의 방장이 아닙니다."),
            @ApiResponse(code = 2022 ,message ="존재하지 않는 배달 파티 멤버입니다."),
            @ApiResponse(code = 2204 ,message ="존재하지 않는 회원 id 입니다."),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PatchMapping("/delivery-party/chief")
    public BaseResponse<PatchLeaveChiefRes> chiefLeaveDeliveryParty(@Validated @RequestBody PatchLeaveChiefReq req, HttpServletRequest request) {
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        PatchLeaveChiefRes res = deliveryPartyService.chiefLeaveDeliveryParty(req.getUuid(), req.getNickName(),jwtInfo.getUserId());
        return new BaseResponse<>(res);
    }

    @ApiOperation(value = "마감 : 배달파티 수동 매칭 마감", notes = "매칭 마감시킬 배달 파티의 아이디를 받아 배달 파티 매칭 status를 FINISH로 바꿀 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code =2616 ,message ="파티 매칭 마감을 할 수 없는 유저이거나 이미 마감된 상태입니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PatchMapping("/delivery-party/{uuid}/matching-status")
    public BaseResponse<PatchDeliveryPartyMatchingStatusRes> patchDeliveryPartyMatchingStatus(@PathVariable String uuid, HttpServletRequest request) {
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        PatchDeliveryPartyMatchingStatusRes response = deliveryPartyService.patchDeliveryPartyMatchingStatus(uuid, jwtInfo.getUserId());
        return new BaseResponse<>(response);
    }


    @ApiOperation(value = "제일 최근에 들어간 진행중인 배달파티 리스트 ", notes = "가장 최근에 참여한 진행 중인 배달 파티들을 가져온다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @GetMapping("/delivery-parties/recent/ongoing")
    public BaseResponse<List<GetRecentOngoingPartiesRes>> getRecentOngoingDeliveryParties(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        List<GetRecentOngoingPartiesRes> res = deliveryPartyService.getRecentOngoingDeliveryParties(jwtInfo.getUserId());
        return new BaseResponse<>(res);
    }

    @ApiOperation(value = "사용자가 진행했던 배달파티 리스트", notes = "사용자가 참여 or 진행했던 배달파티들을 가져온다.(현재는 비활성화 상태인 배달파티) \n"+
            "cursor값을 넣어 줘야 합니다.(cursor값 넣지 않으면 4000 에러 발생) \n"+"URL 예시 : https://geeksasaeng.shop/delivery-parties/end?cursor=2 \n")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message = "요청에 성공하였습니다."),
            @ApiResponse(code = 4000,message = "서버 오류입니다."),
            @ApiResponse(code = 2009, message = "존재하지 않는 멤버입니다.")
    })
    @GetMapping("/delivery-parties/end")
    public BaseResponse<GetEndedDeliveryPartiesRes> getEndedDeliveryParties(HttpServletRequest request,@RequestParam int cursor){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        GetEndedDeliveryPartiesRes getEndedDeliveryPartiesRes = deliveryPartyService.getEndedDeliveryParties(jwtInfo.getUserId(),cursor);
        return new BaseResponse<>(getEndedDeliveryPartiesRes);
    }
}
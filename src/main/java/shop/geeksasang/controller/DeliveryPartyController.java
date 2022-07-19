package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.deliveryParty.*;
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
    @PostMapping("/delivery-party")
    public BaseResponse<PostDeliveryPartyRes> registerDeliveryParty(@Validated @RequestBody PostDeliveryPartyReq dto,  HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        PostDeliveryPartyRes postDeliveryPartyRes = deliveryPartyService.registerDeliveryParty(dto, jwtInfo);
        return new BaseResponse<>(postDeliveryPartyRes);
    }

    //배달파티 조회: 전체목록
    @ApiOperation(value = "전체 배달파티 조회", notes = "cursor은 0부터 시작. dormitoryId는 현재 대학교 id. 예시 : https://geeksasaeng.shop/1/delivery-parties?cursor=0  ")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code=4000, message = "서버 오류입니다.")
    })
    @GetMapping("/{dormitoryId}/delivery-parties")
    public BaseResponse<List<GetDeliveryPartiesRes>> getAllDeliveryParty(@PathVariable int dormitoryId, @RequestParam int cursor){
        List<GetDeliveryPartiesRes> response = deliveryPartyService.getDeliveryPartiesByDormitoryId(dormitoryId, cursor);
        return new BaseResponse<>(response);
    }

    //배달파티 조회: 상세조회
    @ApiOperation(value = "조회: 배달파티 상세조회", notes = "배달파티 게시물을 선택하면 상세 정보들을 볼 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 파티입니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @GetMapping("/delivery-party/{partyId}")
    public BaseResponse<GetDeliveryPartyDetailRes> getDeliveryPartyDetailById(@PathVariable("partyId") int partyId){
        GetDeliveryPartyDetailRes response = deliveryPartyService.getDeliveryPartyDetailById(partyId);

        return new BaseResponse<>(response);
    }

    //배달파티 조회: 상세조회
    @ApiOperation(value = "조회: 조건에 따른 배달파티 조회", notes = "배달파티 게시물을 조건(배달 파티 최대 인원수, 배달 시간대)조건에 맞춰 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    //배달파티 조회: 조건에 따른 검색
    @GetMapping("/{dormitoryId}/delivery-parties/conditions")
    public BaseResponse<List<GetDeliveryPartiesByConditionsRes>> GetDeliveryPartyByConditions(@PathVariable int dormitoryId,
                                                             @RequestParam int cursor, @RequestParam(required = false) String orderTimeCategory, @RequestParam(required = false) Integer maxMatching){
        List<GetDeliveryPartiesByConditionsRes> response = deliveryPartyService.getDeliveryPartiesByConditions(dormitoryId, cursor, orderTimeCategory, maxMatching);
        return new BaseResponse<>(response);
    }

    //배달파티 조회: 검색어로 조회
    @ApiOperation(value = "조회 : 검색어를 포함하는 배달파티 목록 조회", notes = "해당 기숙사의 배달파티 목록울 검색어로 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code=2205,message = "검색어를 입력해주세요"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @GetMapping("/{dormitoryId}/delivery-parties/keyword/{keyword}")
    public BaseResponse<List<GetDeliveryPartiesByKeywordRes>> getDeliveryPartiesByKeyword(@PathVariable("dormitoryId") int dormitoryId, @PathVariable("keyword") String keyword,@RequestParam int cursor){
        List<GetDeliveryPartiesByKeywordRes> response = deliveryPartyService.getDeliveryPartiesByKeyword(dormitoryId, keyword, cursor);
        return new BaseResponse<>(response);
    }
}
package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.domain.OrderTimeCategoryType;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.dto.deliveryParty.*;
import shop.geeksasang.service.DeliveryPartyService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.util.List;

import static shop.geeksasang.config.exception.BaseResponseStatus.NOT_EXISTS_ORDER_TIME_CATEGORY;

@RestController
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class DeliveryPartyControllor {

    private final DeliveryPartyService deliveryPartyService;

    //배달 파티 생성
    @PostMapping("/deliveryParty")
    public BaseResponse<PostDeliveryPartyRes> registerDeliveryParty(@RequestBody PostDeliveryPartyReq dto){
        DeliveryParty deliveryParty = deliveryPartyService.registerDeliveryParty(dto);

        PostDeliveryPartyRes postDeliveryPartyRes = PostDeliveryPartyRes.toDto(deliveryParty);
        return new BaseResponse<>(postDeliveryPartyRes);
    }

    //배달파티 조회: 전체목록
    @ApiOperation(value = "전체 배달파티 조회", notes = "cursor은 0부터 시작. domitoryId는 현재 대학교 id. 예시 : https://geeksasaeng.shop/1/delivery-parties?cursor=0  ")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code=4000, message = "서버 오류입니다.")
    })
    @NoIntercept
    @GetMapping("/{dormitoryId}/delivery-parties")
    public BaseResponse<List<GetDeliveryPartiesRes>> getAllDeliveryParty(@PathVariable int dormitoryId, @RequestParam int cursor){
        List<GetDeliveryPartiesRes> response = deliveryPartyService.getDeliveryPartiesByDormitoryId(dormitoryId, cursor);
        return new BaseResponse<>(response);
    }


//    //배달파티 조회:
//    @GetMapping("/get/detail")
//    public BaseResponse<DeliveryParty> getDeliveryPartyDetailById(@RequestParam int partyId){
//        DeliveryParty deliveryParty = deliveryPartyService.getDeliveryParty(partyId);
//        return new BaseResponse<>(deliveryParty);
//    }

    //배달파티 조회: 상세조회
    @NoIntercept
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

    // 배달파티 조회: 인원수
    @ApiOperation(value = "조회 : 배달파티 목록 인원수에 따라 조회", notes = "해당 기숙사의 배달파티 목록을 인원수에 따라 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code =2604 ,message ="지정된 값이 아닙니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @NoIntercept
    @GetMapping("/{dormitoryId}/delivery-parties/{maxMatching}")
    public BaseResponse<List<GetDeliveryPartyByMaxMatchingRes>> getDeliveryPartyByMaxMatching(@PathVariable int dormitoryId, @PathVariable int maxMatching, @RequestParam("cursor") int cursor){
        List<GetDeliveryPartyByMaxMatchingRes> response = deliveryPartyService.getDeliveryPartyByMaxMatching(dormitoryId, maxMatching, cursor);
        return new BaseResponse<>(response);
    }

    // 배달파티 조회: orderTimeType
    @ApiOperation(value = "조회 : 배달파티 목록 주문 시간대에 따라 조회", notes = "해당 기숙사의 배달파티 목록을 주문 시간대에 따라 조회할 수 있다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code =2608 ,message ="존재하지 않는 시간 카테고리 입니다"),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @NoIntercept
    @GetMapping("/{dormitoryId}/delivery-parties/filter/{orderTimeCategory}")
    public BaseResponse<List<GetDeliveryPartyByOrderTimeRes>> GetDeliveryPartyByOrderTime(@PathVariable int dormitoryId, @PathVariable String orderTimeCategory, @RequestParam("cursor") int cursor){
        // enum값 아닌 것 들어올때 처리 - 리팩토링 대상
        try{
            System.out.println(OrderTimeCategoryType.valueOf(orderTimeCategory));
        }catch(IllegalArgumentException e){
            throw new BaseException(NOT_EXISTS_ORDER_TIME_CATEGORY);
        }
        List<GetDeliveryPartyByOrderTimeRes> response = deliveryPartyService.getDeliveryPartyByOrderTime(dormitoryId, cursor, orderTimeCategory);
        return new BaseResponse<>(response);
    }

}

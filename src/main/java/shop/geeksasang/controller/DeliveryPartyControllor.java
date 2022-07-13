package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.domain.DeliveryParty;
import shop.geeksasang.dto.deliveryParty.GetDeliveryPartiesRes;
import shop.geeksasang.dto.deliveryParty.GetDeliveryPartyDetailRes;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyReq;
import shop.geeksasang.dto.deliveryParty.PostDeliveryPartyRes;
import shop.geeksasang.service.DeliveryPartyService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.util.List;

@RestController
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class DeliveryPartyControllor {

    private final DeliveryPartyService deliveryPartyService;

    //배달 파티 생성
    @PostMapping
    public BaseResponse<PostDeliveryPartyRes> registerDeliveryParty(@RequestBody PostDeliveryPartyReq dto){
        DeliveryParty deliveryParty = deliveryPartyService.registerDeliveryParty(dto);

        PostDeliveryPartyRes postDeliveryPartyRes = PostDeliveryPartyRes.toDto(deliveryParty);

        return new BaseResponse<>(postDeliveryPartyRes);
    }

    //배달파티 조회: 전체목록
    @NoIntercept
    @GetMapping("/{domitoryId}/delivery-parties")
    public BaseResponse<List<GetDeliveryPartiesRes>> getAllDeliveryParty(@PathVariable int domitoryId, @RequestParam int cursor){
        List<GetDeliveryPartiesRes> response = deliveryPartyService.getDeliveryPartiesByDomitoryId(domitoryId, cursor);
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
            @ApiResponse(code = 2010, message = "존재하지 않는 파티입니다.")
    })
    @GetMapping("/delivery-party/{partyId}")
    public BaseResponse<GetDeliveryPartyDetailRes> getDeliveryPartyDetailById(@PathVariable("partyId") int partyId){
        GetDeliveryPartyDetailRes response = deliveryPartyService.getDeliveryPartyDetailById(partyId);

        return new BaseResponse<>(response);
    }

}

package shop.geeksasang.controller.common;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.commercial.GetCommercialsRes;
import shop.geeksasang.service.common.CommercialService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.util.List;


@RequestMapping("/commercials")
@RestController
@RequiredArgsConstructor
public class CommercialController {

    private final CommercialService commercialService;

    @ApiOperation(value = "전체 광고 조회", notes = "전체 광고 이미지를 가져오는 api입니다.")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
    })
    @NoIntercept
    @GetMapping
    public BaseResponse<List<GetCommercialsRes>> getCommercials(){
        List<GetCommercialsRes> response = commercialService.getCommercials();
        return new BaseResponse<>(response);
    }
}

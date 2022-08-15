package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.notification.get.GetAnnouncementDetailReq;
import shop.geeksasang.dto.notification.get.GetAnnouncementDetailRes;
import shop.geeksasang.service.AnnouncementService;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class AnnouncementController {

    private final AnnouncementService announcementService;

    @ApiOperation(value = "공지사항 상세조회",
            notes = "공지사항 id 로 공지사항 상세조회를 한다.")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2204 ,message ="존재하지 않는 회원 id 입니다."),
            @ApiResponse(code = 2206 ,message ="존재하지 않는 공지사항입니다."),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PostMapping("/announcement/detail")
    public BaseResponse<GetAnnouncementDetailRes> getAnnouncementDetail(@Validated @RequestBody GetAnnouncementDetailReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        GetAnnouncementDetailRes res = announcementService.getAnnouncementDetail(dto);
        return new BaseResponse<>(res);
    }
}

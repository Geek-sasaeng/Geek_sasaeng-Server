package shop.geeksasang.controller.announcement;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.announcement.get.GetAnnouncementDetailReq;
import shop.geeksasang.dto.announcement.get.GetAnnouncementDetailRes;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.dto.announcement.get.GetAnnouncementRes;
import shop.geeksasang.service.announcement.AnnouncementService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor // final로 선언 된 것 자동으로 @Autowired와 같은 기능
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @ApiOperation(value = "공지사항 전체 조회", notes = "(jwt 토큰 필요)마이페이지-공지사항에서 공지사항을 전체 조회")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 4000 ,message ="서버 오류입니다.")
    })
    @GetMapping("/announcements")
    public BaseResponse<List<GetAnnouncementRes>> getAnnouncements(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        List<GetAnnouncementRes> announcements = announcementService.getAnnouncements(jwtInfo);
        return new BaseResponse<>(announcements);
    }

    @ApiOperation(value = "공지사항 상세조회",
            notes = "공지사항 id 로 공지사항 상세조회를 한다.")
    @ApiResponses({
            @ApiResponse(code = 1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2009, message ="존재하지 않는 멤버입니다"),
            @ApiResponse(code = 2206 ,message ="존재하지 않는 공지사항입니다."),
            @ApiResponse(code = 4000 ,message = "서버 오류입니다.")
    })
    @PostMapping("/announcement/detail")
    public BaseResponse<GetAnnouncementDetailRes> getAnnouncementDetail(@Validated @RequestBody GetAnnouncementDetailReq dto, HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        int memberId = jwtInfo.getUserId();
        GetAnnouncementDetailRes res = announcementService.getAnnouncementDetail(dto, memberId);
        return new BaseResponse<>(res);
    }
}

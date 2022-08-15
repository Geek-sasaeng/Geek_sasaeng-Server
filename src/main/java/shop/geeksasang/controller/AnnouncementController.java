package shop.geeksasang.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.announcement.get.GetAnnouncementRes;
import shop.geeksasang.service.AnnouncementService;

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
}

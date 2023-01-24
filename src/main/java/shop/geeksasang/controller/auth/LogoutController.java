package shop.geeksasang.controller.auth;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.service.auth.LogoutService;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/logout")
@RestController
@RequiredArgsConstructor
@Slf4j
public class LogoutController {

    private final LogoutService logoutService;

    @ApiOperation(value = "로그아웃 api", notes = "로그아웃 시 사용하는 api")
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하였습니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다."),
            @ApiResponse(code=2009, message = "존재하지 않는 멤버입니다")
    })
    @DeleteMapping
    public BaseResponse<String> logout(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");

        logoutService.logout(jwtInfo.getUserId());
        return new BaseResponse<>("요청에 성공하였습니다.");
    }
}

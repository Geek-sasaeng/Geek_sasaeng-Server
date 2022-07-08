package shop.geeksasang.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.domain.BaseEntity;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.LoginReq;
import shop.geeksasang.dto.login.LoginRes;
import shop.geeksasang.service.LoginService;
import shop.geeksasang.utils.jwt.NoIntercept;


@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
@Slf4j
@NoIntercept
public class LoginController {

    private final LoginService loginService;

    @NoIntercept
    @ApiOperation(
            value="로그인",
            notes="사용자의 id,비밀번호를 입력받아 jwt토큰을 반환한다. "
    )
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2011 ,message ="비밀번호가 틀립니다."),
            @ApiResponse(code =2400 ,message ="존재하지 않는 아이디입니다."),
    })
    @PostMapping
    public BaseResponse<LoginRes> login(@Validated @RequestBody LoginReq dto){
        LoginRes login = loginService.login(dto);
        return new BaseResponse<>(login);
    }

}

package shop.geeksasang.controller;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.PostLoginReq;
import shop.geeksasang.dto.login.PostLoginRes;
import shop.geeksasang.dto.login.PostSocialLoginReq;
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
            @ApiResponse(code =2012 ,message ="탈퇴한 회원입니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PostMapping
    public BaseResponse<PostLoginRes> login(@Validated @RequestBody PostLoginReq dto){
        PostLoginRes login = loginService.login(dto);
        return new BaseResponse<>(login);
    }

    @NoIntercept
    @ApiOperation(
            value="소셜 로그인",
            notes="네이버 로그인 요청 Request를 처리 해 토큰 값에 유효한 사용자 아이디가 있으면 jwt토큰을 반환한다. "
    )
    @ApiResponses({
            @ApiResponse(code =1000 ,message ="요청에 성공하셨습니다."),
            @ApiResponse(code =2011 ,message ="비밀번호가 틀립니다."),
            @ApiResponse(code =2400 ,message ="존재하지 않는 아이디입니다."),
            @ApiResponse(code =2012 ,message ="탈퇴한 회원입니다."),
            @ApiResponse(code=4000,message = "서버 오류입니다.")
    })
    @PostMapping("/social")
    public BaseResponse<PostLoginRes> socialLogin(@Validated @RequestBody PostSocialLoginReq dto){
        PostLoginRes login = loginService.socialLogin(dto);
        return new BaseResponse<>(login);
    }
}

package shop.geeksasang.controller.auth;

import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.login.JwtInfo;
import shop.geeksasang.dto.login.JwtResponse;
import shop.geeksasang.dto.login.post.PostLoginReq;
import shop.geeksasang.dto.login.post.PostLoginRes;
import shop.geeksasang.dto.login.post.PostSocialLoginReq;
import shop.geeksasang.service.auth.LoginService;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;


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
            notes="사용자의 id,비밀번호를 입력받아 jwt토큰을 반환한다. " +
                    "loginStatus가 NEVER인 경우에만 jwt,nickname,loginStatus를 반환하고" +
                    " NOTNEVER인 경우 jwt,nickname,loginStatus,dormitoryId,dormitoryName을 반환합니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2011, message ="비밀번호가 틀립니다."),
            @ApiResponse(code = 2400, message ="존재하지 않는 아이디입니다."),
            @ApiResponse(code = 2012, message ="탈퇴한 회원입니다."),
            @ApiResponse(code= 4000, message = "서버 오류입니다.")
    })
    @PostMapping
    public BaseResponse<PostLoginRes> login(@Validated @RequestBody PostLoginReq dto){
        PostLoginRes login = loginService.login(dto);
        return new BaseResponse<>(login);
    }

    // 네이버 로그인
    @NoIntercept
    @ApiOperation(
            value="소셜 로그인",
            notes="네이버 로그인 토큰을 받아서 로그인을 진행한다, 토큰 userIdx가 DB에 없으면 회원가입 화면으로 이동한다. (2807 코드 참고) "
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2011, message ="비밀번호가 틀립니다."),
            @ApiResponse(code = 2400,message ="존재하지 않는 아이디입니다."),
            @ApiResponse(code = 2012,message ="탈퇴한 회원입니다."),
            @ApiResponse(code = 2807,message ="존재하지 않는 아이디 입니다. 네이버 회원가입 화면으로 이동합니다.."),
            @ApiResponse(code = 4000, message = "서버 오류입니다.")
    })
    @PostMapping("/social")
    public BaseResponse<PostLoginRes> socialLogin(@Validated @RequestBody PostSocialLoginReq dto){
        PostLoginRes login = loginService.naverLogin(dto);
        return new BaseResponse<>(login);
    }

    // 자동 로그인
    // jwt를 이용해서 member 정보 반환
    @ApiOperation(
            value="소셜 로그인",
            notes="네이버 로그인 토큰을 받아서 로그인을 진행한다, 토큰 userIdx가 DB에 없으면 회원가입 화면으로 이동한다. (2807 코드 참고) "
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message ="요청에 성공하셨습니다."),
            @ApiResponse(code = 2204, message ="존재하지 않는 회원 id 입니다."),
            @ApiResponse(code = 2001, message ="header에 JWT가 없습니다."),
            @ApiResponse(code = 2002,message ="유효하지 않은 JWT입니다. 재로그인 바랍니다."),
            @ApiResponse(code = 2003,message ="만료기간이 지난 JWT입니다. 재로그인 바랍니다.")
    })
    @PostMapping("/auto")
    public BaseResponse<JwtResponse> socialLogin(HttpServletRequest request){
        JwtInfo jwtInfo = (JwtInfo) request.getAttribute("jwtInfo");
        JwtResponse memberInfo = loginService.autoLogin(jwtInfo);
        return new BaseResponse<>(memberInfo);
    }
}

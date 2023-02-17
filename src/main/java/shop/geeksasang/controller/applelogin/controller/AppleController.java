package shop.geeksasang.controller.applelogin.controller;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.controller.applelogin.model.*;
import shop.geeksasang.controller.applelogin.service.AppleServiceImpl;
import shop.geeksasang.utils.jwt.NoIntercept;

import javax.servlet.http.HttpServletRequest;
import java.security.NoSuchAlgorithmException;

@RequiredArgsConstructor
@Controller
public class AppleController {

    private Logger logger = LoggerFactory.getLogger(AppleController.class);

    private final AppleServiceImpl appleService;

    /**
     * Apple 회원가입
     * privateKey 로 사용자 개인 정보와 refreshToken 발급받기
     * @return
     */

    @NoIntercept
    @ApiOperation(value = "회원가입")
    @PostMapping(value = "/apple-login")
    @ResponseBody
    public BaseResponse<TokenResponse> signUpApple(
            String state, String code, String user, @RequestParam("id_token") String idToken
    ) throws NoSuchAlgorithmException {

        ServicesResponse servicesResponse = new ServicesResponse();
        servicesResponse.setCode(code);
        servicesResponse.setId_token(idToken);
        System.out.println("user = " + user);
        servicesResponse.setUser(null);
        servicesResponse.setState(state);

        TokenResponse tokenResponse = appleService.requestCodeValidations(servicesResponse, null);
        return new BaseResponse<>(tokenResponse);
    }

    /**
     * Apple 로그인
     *
     * @return
     */
    @ApiOperation(value = "로그인")
    @PostMapping(value = "/log-in/apple")
    @ResponseBody
    public BaseResponse<TokenResponse> logInApple(@RequestBody AppleLoginReq appleLoginReq) throws NoSuchAlgorithmException {

        if (appleLoginReq == null) { // TODO 예외처리
            System.out.println("요청 값이 없습니다.");
            return null;
        }
        TokenResponse tokenResponse = appleService.requestCodeValidations(appleLoginReq.getServicesResponse(), appleLoginReq.getRefreshToken());
        return new BaseResponse<>(tokenResponse);
    }

    /**
     * Apple 계정 탈퇴
     *
     * @return
     */
    @ApiOperation(value = "회원탈퇴")
    @PostMapping(value = "/delete/apple")
    @ResponseBody
    public BaseResponse<String> deleteUserApple(@RequestBody DeleteUserReq deleteUserReq) throws NoSuchAlgorithmException {

        appleService.deleteUser(deleteUserReq);

        return new BaseResponse<>("애플 탈퇴를 성공했습니다.");
    }

    /**
     * refresh_token 유효성 검사
     *
     * @param client_secret
     * @param refresh_token
     * @return
     * refresh_token은 만료되지 않기 때문에
     * 권한이 필요한 요청일 경우 굳이 매번 애플 ID 서버로부터 refresh_token을 통해 access_token을 발급 받기보다는
     * 유저의 refresh_token을 따로 DB나 기타 저장소에 저장해두고 캐싱해두고 조회해서 검증하는편이 성능면에서 낫다고 함..
     * 우리도 db에 refresh token을 따로 저장해서 사용하므로 이 메소드는 필요 없지 않을까..
     */
//    @PostMapping(value = "/refresh")
//    @ResponseBody
//    public TokenResponse refreshRedirect(@RequestParam String client_secret, @RequestParam String refresh_token) {
//        return appleService.requestCodeValidations(client_secret, null, refresh_token);
//    }

    /**
     * Apple 유저의 이메일 변경, 서비스 해지, 계정 탈퇴에 대한 Notifications을 받는 Controller (SSL - https (default: 443))
     *
     * @param appsResponse
     */
    @PostMapping(value = "/apps/to/endpoint")
    @ResponseBody
    public void appsToEndpoint(@RequestBody AppsResponse appsResponse) {
        System.out.println("애플 계정 탈퇴했습니다.");
        logger.debug("[/path/to/endpoint] RequestBody ‣ " + appsResponse.getPayload());
    }

}

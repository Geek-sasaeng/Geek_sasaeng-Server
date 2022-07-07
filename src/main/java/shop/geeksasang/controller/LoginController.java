package shop.geeksasang.controller;

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

import java.util.LinkedHashMap;

@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
@Slf4j
@NoIntercept
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @NoIntercept
    public BaseResponse<LoginRes> login(@Validated @RequestBody LoginReq dto){
        LoginRes login = loginService.login(dto);
        return new BaseResponse<>(login);
    }

}

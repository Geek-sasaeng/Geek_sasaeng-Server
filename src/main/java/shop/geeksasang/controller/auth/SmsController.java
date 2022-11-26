package shop.geeksasang.controller.auth;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import shop.geeksasang.config.response.BaseResponse;
import shop.geeksasang.dto.sms.PostSmsReq;
import shop.geeksasang.dto.sms.NaverApiSmsRes;
import shop.geeksasang.dto.sms.PostVerifySmsReq;
import shop.geeksasang.dto.sms.PostVerifySmsRes;
import shop.geeksasang.service.auth.SmsService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.net.URISyntaxException;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @ApiOperation(value = "SMS 인증 문자 요쳥", notes = "SMS 인증을 위해 인증 메세지를 요청한다.")
    @ApiResponses({
            @ApiResponse(code =1001 ,message = "SMS 요청에 성공했습니다."),
            @ApiResponse(code =2015 ,message = "일일 최대 전송 횟수를 초과했습니다.T"),
            @ApiResponse(code =2016 ,message = "이메일 인증을 하지 못한 유저입니다. 이메일 인증을 해주세요."),
            @ApiResponse(code =4001 ,message = "SMS API 연동 오류입니다."),
            @ApiResponse(code =4002 ,message = "SMS API 연동 준비 오류입니다."),
    })
    @NoIntercept
    @PostMapping
    public BaseResponse<NaverApiSmsRes> sendSms(@Validated @RequestBody PostSmsReq request) throws  URISyntaxException, JsonProcessingException {
        smsService.sendSms(request.getRecipientPhoneNumber(), request.getUuid());
        return new BaseResponse<>(SMS_SEND_SUCCESS);
    }


    @ApiOperation(value = "SMS 인증 요쳥", notes = "SMS 인증을 요청한다.")
    @ApiResponses({
            @ApiResponse(code =1002 ,message = "SMS 인증에 성공했습니다."),
            @ApiResponse(code =2013 ,message = "인증번호가 틀렸습니다."),
    })
    @NoIntercept
    @PostMapping("/verification")
    public BaseResponse<PostVerifySmsRes> verifySms(@Validated @RequestBody PostVerifySmsReq request){
        PostVerifySmsRes postVerifySmsRes = smsService.verifySms(request.getVerifyRandomNumber(), request.getRecipientPhoneNumber());
        return new BaseResponse<>(postVerifySmsRes);
    }
}

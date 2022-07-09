package shop.geeksasang.controller;

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
import shop.geeksasang.service.SmsService;
import shop.geeksasang.utils.jwt.NoIntercept;

import java.net.URISyntaxException;

import static shop.geeksasang.config.exception.BaseResponseStatus.*;

@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @NoIntercept
    @PostMapping
    public BaseResponse<NaverApiSmsRes> sendSms(@Validated @RequestBody PostSmsReq request) throws  URISyntaxException, JsonProcessingException {
        smsService.sendSms(request.getRecipientPhoneNumber(), request.getUuid());
        return new BaseResponse<>(SMS_VERIFICATION_SUCCESS);
    }

    @NoIntercept
    @PostMapping("/verification")
    public BaseResponse<PostVerifySmsRes> verifySms(@Validated @RequestBody PostVerifySmsReq request){
        PostVerifySmsRes smsResponse = smsService.verifySms(request.getVerifyRandomNumber(), request.getRecipientPhoneNumber());
        return new BaseResponse<>(smsResponse);
    }

}

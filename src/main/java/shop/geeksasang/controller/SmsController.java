package shop.geeksasang.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
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

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RequestMapping("/sms")
@RestController
@RequiredArgsConstructor
public class SmsController {

    private final SmsService smsService;

    @PostMapping
    public BaseResponse<NaverApiSmsRes> sendSms(@Validated @RequestBody PostSmsReq request) throws UnsupportedEncodingException, NoSuchAlgorithmException, URISyntaxException, InvalidKeyException, JsonProcessingException {
        NaverApiSmsRes smsResponse = smsService.sendSms(request.getRecipientPhoneNumber());
        return new BaseResponse<>(smsResponse);
    }

    @PostMapping("/validation")
    public BaseResponse<Object> verifySms(@Validated @RequestBody PostVerifySmsReq request){
        PostVerifySmsRes smsResponse = smsService.verifySms(request.getVerifyRandomNumber(), request.getRecipientPhoneNumber());
        return new BaseResponse<>(smsResponse);
    }
}

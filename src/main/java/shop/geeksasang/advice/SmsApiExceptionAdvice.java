package shop.geeksasang.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

//@Slf4j
@RestController
public class SmsApiExceptionAdvice {
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({ UnsupportedEncodingException.class, NoSuchAlgorithmException.class, InvalidKeyException.class, JsonProcessingException.class} )
    public BaseResponse<BaseResponseStatus> allHandleException(Exception e) {
        //log.error("Handle All Exception: {}", e.getMessage());
        return new BaseResponse<>(BaseResponseStatus.SMS_API_ERROR);
   }
}

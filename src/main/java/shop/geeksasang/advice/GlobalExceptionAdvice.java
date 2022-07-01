package shop.geeksasang.advice;

import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionAdvice {

//    @ExceptionHandler(BaseException.class)
//    public BaseResponse<BaseResponseStatus> baseException(BaseException e) {
//        log.error("Handle CommonException: {}", e.getMessage());
//        return new BaseResponse<>(e.getStatus());
//    }
//
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    @ExceptionHandler(Exception.class)
//    public BaseResponse<BaseResponseStatus> allHandleException(Exception e) {
//        log.error("Handle All Exception: {}", e.getMessage());
//        return new BaseResponse<>(BaseResponseStatus.INTERNAL_SERVER_ERROR);
//    }
}

package shop.geeksasang.advice;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.http.fileupload.impl.FileSizeLimitExceededException;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import org.springframework.web.multipart.MaxUploadSizeExceededException;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.response.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;

import java.net.URISyntaxException;

import static shop.geeksasang.config.exception.response.BaseResponseStatus.*;

@Slf4j
@RequiredArgsConstructor
@RestControllerAdvice
public class GlobalExceptionAdvice {

    private final MessageSource ms;

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse MemberNotFoundException(MethodArgumentNotValidException exception){
        BindingResult bindingResult = exception.getBindingResult();
        String[] codes = bindingResult.getAllErrors().get(0).getCodes();
        for (String code : codes) {
            System.out.println("code = " + code);
        }

        String code = codes[1];
        return new ErrorResponse(ms.getMessage(code,null,null));
    }

    @Getter
    public class ErrorResponse {
        String message;
        public ErrorResponse(String message) {
            this.message = message;
        }
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<BaseResponseStatus> baseException(BaseException e) {
        log.error("Handle CommonException: {}", e.getMessage());
        //e.printStackTrace();
        return new BaseResponse<>(e.getStatus());
    }

    @ExceptionHandler(FileSizeLimitExceededException.class)
    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    public BaseResponse<BaseResponseStatus> fileSizeException(FileSizeLimitExceededException e){
        log.error("FileSizeLimitExceededException: {}", e.getMessage());
        return new BaseResponse<>(FILE_SIZE_LIMIT_EXCEED);
    }

    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public BaseResponse<BaseResponseStatus> maxUploadSizeException(MaxUploadSizeExceededException e){
        log.error("Handle maxUploadSizeException: {}", e.getMessage());
        return new BaseResponse<>(MAX_UPLOAD_SIZE_LIMIT_EXCEED);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({JsonProcessingException.class, URISyntaxException.class} )
    public BaseResponse<BaseResponseStatus> smsHandleException(Exception e) {
        //log.error("Handle All Exception: {}", e.getMessage());
        return new BaseResponse<>(SMS_API_ERROR);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public BaseResponse<BaseResponseStatus> allHandleException(Exception e) {
        log.error("Handle All Exception: {}", e.getMessage());
        e.printStackTrace();
        return new BaseResponse<>(INTERNAL_SERVER_ERROR);
    }
}
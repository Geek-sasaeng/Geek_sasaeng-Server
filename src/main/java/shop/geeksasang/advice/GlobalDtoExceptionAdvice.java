package shop.geeksasang.advice;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.hibernate.type.LiteralType;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shop.geeksasang.config.exception.BaseException;
import shop.geeksasang.config.exception.BaseResponseStatus;
import shop.geeksasang.config.response.BaseResponse;

import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestControllerAdvice
@RequiredArgsConstructor
public class GlobalDtoExceptionAdvice {

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
}
